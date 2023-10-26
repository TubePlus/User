package com.example.user_service.user.application.service;

import com.example.user_service.user.application.ports.input.*;
import com.example.user_service.user.application.ports.output.dto.*;
import com.example.user_service.user.application.ports.output.UserPort;
import com.example.user_service.user.application.service.response.GetMyChannelDto;
import com.example.user_service.user.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements
        LogInUseCase, SignUpUseCase, ChangeUsernameUseCase, UserInfoUseCase, DuplicateUsernameUseCase
        /*, RegisterCreatorUseCase*/ {

    private final UserPort userPort;
    private final YoutubeService youtubeService;

    @Override
    public LogInDto logInUser(LogInQuery logInQuery) throws JsonProcessingException {

        // youtube data api로 profile image 받아오기.
        GetMyChannelDto dto = youtubeService.getMyProfileImage(logInQuery.getToken());

        User user = userPort.logInUser(User.logInUser(logInQuery.getEmail(), dto.getUrl()));
        //todo: redis에 토큰 저장하는 로직 추가해야함.
        return LogInDto.formLogInDto(user); // return 값으로 사용할 Dto
    }

    @Override
    public SignUpDto signUpUser(SignUpQuery signUpQuery) throws JsonProcessingException {

        // username 중복 체크
        checkDuplicateUsername(signUpQuery.getUsername());

        // 신규유저의 uuid 생성
        String newUuid = generateUuid();

        // youtube data api로 profile image 받아오기.
        GetMyChannelDto dto = youtubeService.getMyProfileImage(signUpQuery.getToken());

        User user = userPort.signUpUser(User.signUpUser(
                signUpQuery.getUsername(),
                dto.getUrl(),
                signUpQuery.getRole(),
                signUpQuery.getIsCreator(),
                signUpQuery.getEmail(),
                newUuid,
                signUpQuery.getLocale(),
                signUpQuery.getDarkMode(),
                signUpQuery.getSoftDelete()
        ));
        return SignUpDto.formSignUpDto(user); // return 값으로 사용할 Dto
    }

    // 중복 조회
    @Override
    public IsDuplicateDto checkDuplicateName(CheckDuplicateUsernameQuery checkDuplicateUsernameQuery) {

        checkDuplicateUsername(checkDuplicateUsernameQuery.getUsername());
        return IsDuplicateDto.builder()
                .isDuplicate(false)
                .build();
    }

    // username 변경(기존의 username을 새로운 username으로 변경 시 사용)
    @Override
    public ChangeUsernameDto changeUsername(ChangeUsernameQuery changeUsernameQuery) {

        // username 중복 체크
        checkDuplicateUsername(changeUsernameQuery.getUsername());

        User user = userPort.changeUsername(User.changeUsername(
                changeUsernameQuery.getUsername(),
                changeUsernameQuery.getUuid()
        ));
        return ChangeUsernameDto.formUsernameDto(user); // return 값으로 사용할 Dto
    }

//    @Override
//    public RegisterCreatorDto registerCreator(RegisterCreatorQuery registerCreatorQuery) {
//        User user = userPort.registerCreator()
//    }

    @Override
    public UserInfoDto getUserInfo(UserInfoQuery userInfoQuery) {

        User user = userPort.getUserInfo(userInfoQuery.getUuid());
        return UserInfoDto.formUserInfoDto(user); // return 값으로 사용할 Dto
    }

    /**
     * 비즈니스 로직 정의
     */
    // 회원가입 시 유저 uuid 생성
    public String generateUuid() {

        return UUID.randomUUID().toString();
    }

    // username 중복 체크(회원가입 시)
    public void checkDuplicateUsername(String username) {

        userPort.checkDuplicateUsername(username);
    }
}
