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
import org.springframework.web.ErrorResponse;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAdaptor implements UserPort {

    private final UserRepository userRepository;

    // 로그인
    @Override
    public User logInUser(User user) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());
        return userEntity.map(User::userEntityToUser).orElseThrow(
                () -> new BusinessException(ErrorCode.FAIL_LOGIN)
        );
    }

    // 회원가입
    @Transactional
    @Override
    public User signUpUser(User user) {

        UserEntity userEntity = UserEntity.userToUserEntity(user);
        //todo: userToUserEntity 메서드 UserEntity에 정의해야할듯
        return User.userEntityToUser(userRepository.save(userEntity));
    }

    // 유저네임 중복 체크
    @Override
    public Boolean checkDuplicateUsername(String username) {

        // 유저네임이 이미 존재하면 커스텀 에러 반환
        if(userRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.DUPLICATE_USERNAME);
        }
        return false;
    }

    // 구글 인증 아이디 중복 체크(가입 여부 조회를 위한)
    @Override
    public Boolean checkDuplicateGoogleAuth(String googleAuth) {

//        return userRepository.existsByGoogleAuth(googleAuth);
        return true;
    }

    // 유저 회원정보 조회
    @Override
    public User getUserInfo(String uuid) {

//        UserEntity userEntity = userRepository.findByUuid(uuid);
//        return User.userEntityToUser(userEntity);
        return null;
    }
}
