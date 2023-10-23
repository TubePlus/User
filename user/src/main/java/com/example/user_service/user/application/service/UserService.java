package com.example.user_service.user.application.service;

import com.example.user_service.user.application.ports.input.DuplicateCheckUseCase;
import com.example.user_service.user.application.ports.input.LogInUseCase;
import com.example.user_service.user.application.ports.input.SignUpUseCase;
import com.example.user_service.user.application.ports.input.UserInfoUseCase;
import com.example.user_service.user.application.ports.output.dto.DuplicateCheckDto;
import com.example.user_service.user.application.ports.output.dto.LogInDto;
import com.example.user_service.user.application.ports.output.dto.SignUpDto;
import com.example.user_service.user.application.ports.output.UserPort;
import com.example.user_service.user.application.ports.output.dto.UserInfoDto;
import com.example.user_service.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements LogInUseCase, SignUpUseCase, DuplicateCheckUseCase, UserInfoUseCase {

    private final UserPort userPort;

    @Override
    public LogInDto logInUser(LogInQuery logInQuery) {

        User user = userPort.logInUser(User.logInUser(logInQuery.getEmail()));
        return LogInDto.formLogInDto(user); // return 값으로 사용할 Dto
    }

    @Override
    public SignUpDto signUpUser(SignUpQuery signUpQuery) {

        // username 중복 체크
        checkDuplicateUsername(signUpQuery.getUsername());

        User user = userPort.signUpUser(User.signUpUser(
                signUpQuery.getUsername(),
                signUpQuery.getProfileImage(),
                signUpQuery.getRole(),
                signUpQuery.getIsCreator(),
                signUpQuery.getEmail(),
                signUpQuery.getUuid(),
                signUpQuery.getLanguage(),
                signUpQuery.getDarkMode(),
                signUpQuery.getSoftDelete()
        ));
        return SignUpDto.formSignUpDto(user); // return 값으로 사용할 Dto
    }

    @Override // username 중복 체크(기존의 username을 새로운 username으로 변경 시 사용)
    public DuplicateCheckDto checkDuplicateUsername(CheckUsernameQuery checkUsernameQuery) {

        Boolean isDuplicate = userPort.checkDuplicateUsername(checkUsernameQuery.getUsername());
        return DuplicateCheckDto.formDuplicateCheckDto(isDuplicate); // return 값으로 사용할 Dto
    }

    @Override
    public DuplicateCheckDto checkDuplicateGoogleAuth(CheckGoogleAuthQuery checkGoogleAuthQuery) {

        Boolean isDuplicate = userPort.checkDuplicateGoogleAuth(checkGoogleAuthQuery.getGoogleAuth());
        return DuplicateCheckDto.formDuplicateCheckDto(isDuplicate); // return 값으로 사용할 Dto
    }

    @Override
    public UserInfoDto getUserInfo(UserInfoQuery userInfoQuery) {

        User user = userPort.getUserInfo(userInfoQuery.getUuid());
        return UserInfoDto.formUserInfoDto(user); // return 값으로 사용할 Dto
    }

    /**
     * 비즈니스 로직 정의
     */
    
    // username 중복 체크(회원가입 시)
    public Boolean checkDuplicateUsername(String username) {

        return userPort.checkDuplicateUsername(username);
    }

    // Google OAuth ID 중복 체크
    public Boolean checkDuplicateGoogleAuth(String googleAuth) {

        return userPort.checkDuplicateGoogleAuth(googleAuth);
    }
}