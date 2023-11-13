package com.example.user_service.user.adapter.infrastructure.mysql.persistence;

import com.example.user_service.global.error.ErrorCode;
import com.example.user_service.global.error.handler.BusinessException;
import com.example.user_service.user.adapter.infrastructure.mysql.entity.QUserEntity;
import com.example.user_service.user.adapter.infrastructure.mysql.entity.UserEntity;
import com.example.user_service.user.adapter.infrastructure.mysql.repository.UserRepository;
import com.example.user_service.user.application.ports.output.UserPort;
import com.example.user_service.user.domain.StatusType;
import com.example.user_service.user.domain.User;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAdaptor implements UserPort {

    private final UserRepository userRepository;
    private final EntityManager em;

    // 로그인
    @Transactional
    @Override
    public User logInUser(User user) { // todo: 어댑터는 repository와 연결하는 역할만 하도록 변경

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());

        // todo: 아래의 로직은 전부 비즈니스 단으로 이동(UserService)
        // 해당 이메일과 일치하는 유저 데이터 존재하는지 여부 확인
        if (userEntity.isPresent()) {

            // 해당 유저 상태 조회(탈퇴, 정지, 비활성화 등. 비활성화 시 회원복귀 api 호출됨)
            StatusType userStatus = userEntity.get().getStatus();

            switch (userStatus) {

                case INACTIVE:
                    throw new BusinessException(ErrorCode.INACTIVE_USER);

                case BANNED, TEMPORAL_BAN:
                    throw new BusinessException(ErrorCode.BANNED_USER);

                case DELETED:
                    throw new BusinessException(ErrorCode.DELETED_USER);
            }

            // 유저 프로필/핸들러 변경 여부에 따른 업데이트
            updateProfileImageAndHandler(userEntity, user);
        }

        // 일치하는 데이터 없으면 에러 처리
        return userEntity.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_LOGIN_USER)
        );
    }

    // 회원가입
    @Transactional
    @Override
    public User signUpUser(User user) {

        UserEntity userEntity = UserEntity.userToUserEntity(user);
        return User.userEntityToUser(userRepository.save(userEntity));
    }

    // 휴면회원복귀
    @Transactional
    @Override
    public User comeBackUser(User user) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());

        // 유저 상태 ACTIVE로 전환
        userEntity.ifPresent(UserEntity::comeBack);

        // 유저 프로필/핸들러 변경 여부에 따른 업데이트
        updateProfileImageAndHandler(userEntity, user);

        return userEntity.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );
    }

    // 유저네임 변경
    @Transactional
    @Override
    public User changeUsername(User user) {

        // uuid로 해당 유저엔티티 조회
        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());
        // 유저 엔티티의 유저네임 업데이트
        entityUser.ifPresent(entity -> entity.updateUsername(user.getUsername()));
        return entityUser.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );
    }

    // 크리에이터 여부 확인(서버간 API 통신)
    // todo: 서버간 통신 API 사용하지 않을 경우에 삭제해야합니다.
    @Override
    public Boolean checkCreator(User user) {

        // uuid로 해당 유저엔티티 조회
        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());

        return entityUser.isPresent() && entityUser.get().getIsCreator();
    }

    // 크리에이터 등록
    @Transactional
    @Override
    public User registerCreator(User user) {

        // uuid로 해당 유저엔티티 조회
        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());
        // 유저 엔티티의 크리에이터 여부, 크리에이터 카테고리 업데이트
        entityUser.ifPresent(entity -> entity.registerCreator(user.getCategory()));
        return entityUser.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );
    }

    // 크리에이터 카테고리 변경(크리에이터 정보 수정)
    @Transactional
    @Override
    public User changeCreatorCategory(User user) {

        // uuid로 해당 유저엔티티 조회
        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());
        // 크리에이터인지 아닌지 여부 조회
        entityUser.ifPresent(entity -> {
            if (!entity.getIsCreator()) {
                throw new BusinessException(ErrorCode.NOT_CREATOR);
            }
        });
        // 크리에이터 카테고리 업데이트
        entityUser.ifPresent(entity -> entity.updateCategory(user.getCategory()));
        return entityUser.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );
    }

    // 크리에이터 등록 해제
    @Transactional
    @Override
    public User deleteCreator(User user) {

        // uuid로 해당 유저엔티티 조회
        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());
        // 크리에이터인지 아닌지 여부 조회
        entityUser.ifPresent(entity -> {
            if (!entity.getIsCreator()) {
                throw new BusinessException(ErrorCode.NOT_CREATOR);
            }
        });
        // 유저 크리에이터 등록 해제
        entityUser.ifPresent(UserEntity::deleteCreator);
        return entityUser.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );
    }

    // 회원탈퇴
    @Transactional
    @Override
    public User softDeleteUser(User user) {

        // uuid로 해당 유저엔티티 조회
        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());
        // 유저가 존재할 경우 유저 삭제처리
        entityUser.ifPresent(UserEntity::softDelete);
        return entityUser.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );
    }

    // 다크모드 변경
    @Transactional
    @Override
    public User toggleDarkMode(User user) {

        // uuid로 해당 유저엔티티 조회
        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());
        // 유저 다크모드 설정 변경
        entityUser.ifPresent(UserEntity::toggleDarkMode);
        return entityUser.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );
    }

    @Override
    public User autoSearchCreators(User user) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QUserEntity u = QUserEntity.userEntity;

        List<Tuple> result = queryFactory.select(u.username, u.category, u.profileImage, u.bio)
                .from(u)
                .where(u.username.contains(user.getUsername()))
                .limit(5)
                .fetch();
        System.out.println(result);
        return null;
    }

    // 유저 회원정보 조회
    @Override
    public User getUserInfo(User user) {

        // uuid로 해당 유저엔티티 조회
        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());
        return entityUser.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER)
        );
    }

    // 유저 정보 수정
    @Transactional
    @Override
    public User updateUserInfo(User user) {

        Optional<UserEntity> entityUser = userRepository.findByUuid(user.getUuid());
        entityUser.ifPresent(entity -> entity.updateUserInfo(
                user.getUsername(),
                user.getProfileImage(),
                user.getLocale(),
                user.getBio()
        ));
        return entityUser.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    // 유저네임 중복 체크
    @Override
    public void checkDuplicateUsername(String username) {

        // 유저네임이 이미 존재하면 커스텀 에러 반환
        if(userRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.DUPLICATE_USERNAME);
        }
    }

    // 유저 프로필/핸들러 변경 여부에 따른 업데이트
    @Transactional
    public void updateProfileImageAndHandler(Optional<UserEntity> userEntity, User user) {

        // 유저가 존재하면 프로필 이미지 변경 여부 확인 -> 변경 시 프로필 업데이트
        boolean sameProfile = userEntity.get().getProfileImage().equals(user.getProfileImage());
        if (!sameProfile) {
            userEntity.get().updateProfileImage(user.getProfileImage());
        }

        // 유저가 존재하면 유튜브 핸들러 변경 여부 확인 -> 변경 시 유튜브 핸들러 업데이트
        boolean sameYoutubeHandler = userEntity.get().getYoutubeHandler().equals(user.getYoutubeHandler());
        if (!sameYoutubeHandler) {
            userEntity.get().updateYoutubeHandler(user.getYoutubeHandler());
        }
    }
}
