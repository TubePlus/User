package com.example.user_service.user.adapter.infrastructure.mysql.persistence;

import com.example.user_service.global.error.ErrorCode;
import com.example.user_service.global.error.handler.BusinessException;
import com.example.user_service.user.adapter.infrastructure.mysql.entity.UserEntity;
import com.example.user_service.user.adapter.infrastructure.mysql.repository.UserRepository;
import com.example.user_service.user.application.ports.output.UserPort;
import com.example.user_service.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAdaptor implements UserPort {

    private final UserRepository userRepository;

    // 로그인
    @Transactional
    @Override
    public User logInUser(User user) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());

        // 해당 이메일과 일치하는 유저 데이터 존재하는지 여부 확인
        if (userEntity.isPresent()) {
            // 존재하면 프로필 이미지 변경 여부 확인 -> 변경 시 프로필 업데이트
            boolean sameProfile = userEntity.get().getProfileImage().equals(user.getProfileImage());
            if (!sameProfile) {
                userEntity.get().updateProfileImage(user.getProfileImage());
            }
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

    // 유저네임 중복 체크
    @Override
    public void checkDuplicateUsername(String username) {

        // 유저네임이 이미 존재하면 커스텀 에러 반환
        if(userRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.DUPLICATE_USERNAME);
        }
    }

    // 유저 회원정보 조회
    @Override
    public User getUserInfo(String uuid) {

//        UserEntity userEntity = userRepository.findByUuid(uuid);
//        return User.userEntityToUser(userEntity);
        return null;
    }
}
