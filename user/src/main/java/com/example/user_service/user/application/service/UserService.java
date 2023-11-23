package com.example.user_service.user.application.service;

import com.example.user_service.config.kafka.KafkaProducer;
import com.example.user_service.user.application.ports.input.*;
import com.example.user_service.user.application.ports.output.dto.*;
import com.example.user_service.user.application.ports.output.UserPort;
import com.example.user_service.user.application.service.response.GetMyChannelDto;
import com.example.user_service.user.domain.RoleType;
import com.example.user_service.user.domain.StatusType;
import com.example.user_service.user.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements
        LogInUseCase, UsernameUseCase, UserInfoUseCase, CreatorUseCase {

    private final UserPort userPort;
    private final YoutubeService youtubeService;
    private final KafkaProducer kafkaProducer;

    // 로그인
    @Override
    public LogInDto logInUser(LogInQuery logInQuery) throws JsonProcessingException {

        // youtube data api로 채널 정보 받아오기.
        GetMyChannelDto dto = youtubeService.getMyProfileImageAndHandler(logInQuery.getToken());

        User user = userPort.logInUser(User.logInUser(logInQuery.getEmail(), dto.getYoutubeHandler()));
        //todo: redis에 토큰 저장하는 로직 추가해야함.
        return LogInDto.formLogInDto(user); // return 값으로 사용할 Dto
    }

    // 회원가입
    @Override
    public SignUpDto signUpUser(SignUpQuery signUpQuery) throws JsonProcessingException {

        // username 중복 체크
        checkDuplicateUsername(signUpQuery.getUsername());

        // 신규유저의 uuid 생성
        String newUuid = generateUuid();

        // youtube data api로 profile image와 유튜브 핸들러 받아오기.
        GetMyChannelDto dto = youtubeService.getMyProfileImageAndHandler(signUpQuery.getToken());

        User user = userPort.signUpUser(User.signUpUser(
                signUpQuery.getUsername(),
                dto.getUrl(),
                dto.getYoutubeHandler(),
                RoleType.MEMBER,
                false,
                signUpQuery.getEmail(),
                newUuid,
                signUpQuery.getLocale(),
                false,
                StatusType.ACTIVE
        ));
        return SignUpDto.formSignUpDto(user); // return 값으로 사용할 Dto
    }

    // 휴면 유저 복귀
    @Override
    public ComeBackDto comeBackUser(ComeBackQuery comeBackQuery) throws JsonProcessingException {

        // youtube data api로 profile image와 유튜브 핸들러 받아오기.
        GetMyChannelDto dto = youtubeService.getMyProfileImageAndHandler(comeBackQuery.getToken());

        User user = userPort.comeBackUser(User.comeBackUser(
                comeBackQuery.getEmail(),
                dto.getUrl(),
                dto.getYoutubeHandler()
        ));
        return ComeBackDto.formComeBackDto(user);
    }

    // 유저네임 중복 조회
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

    // 크리에이터 등록
    @Override
    public UpdateCreatorDto registerCreator(UpdateCreatorQuery registerCreatorQuery) {

        //todo: youtube api로 크리에이터 조건 충족하는지 확인하는 로직 추가해야함.

        User user = userPort.registerCreator(User.updateCreator(
                registerCreatorQuery.getUuid(),
                registerCreatorQuery.getCategoryName()
        ));
        try {
            kafkaProducer.producerCreateCreator(
                    user.getUuid(),
                    user.getCategory(),
                    user.getProfileImage(),
                    user.getYoutubeHandler(),
                    user.getUsername());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return UpdateCreatorDto.formUpdateCreatorDto(user);
    }

    // 크리에이터 카테고리 수정(크리에이터 정보 수정)
    @Override
    public UpdateCreatorDto changeCreatorCategory(UpdateCreatorQuery changeCreatorCategoryQuery) {

        User user = userPort.changeCreatorCategory(User.updateCreator(
                changeCreatorCategoryQuery.getUuid(),
                changeCreatorCategoryQuery.getCategoryName()
        ));
        return UpdateCreatorDto.formUpdateCreatorDto(user);
    }

    // 크리에이터 등록 해제
    @Override
    public DeleteCreatorDto deleteCreator(DeleteCreatorQuery deleteCreatorQuery) {

        User user = userPort.deleteCreator(User.deleteCreator(deleteCreatorQuery.getUuid()));
        return DeleteCreatorDto.formDeleteCreatorDto(user);
    }

    // 크리에이터 검색 자동완성
    @Override
    public List<AutoSearchCreatorsDto> autoSearchCreators(AutoSearchCreatorsQuery autoSearchCreatorsQuery) {

        return userPort.autoSearchCreators(User.autoSearchCreators(
                autoSearchCreatorsQuery.getUsername()
        ));
    }

    // 유저 정보 조회하기
    @Override
    public ReadUserInfoDto getUserInfo(GetUserInfoQuery getUserInfoQuery) {

        User user = userPort.getUserInfo(User.getUserInfo(getUserInfoQuery.getUuid()));
        return ReadUserInfoDto.formUserInfoDto(user); // return 값으로 사용할 Dto
    }

    // 유저 정보 수정하기
    @Override
    public UpdateUserInfoDto updateUserInfo(UpdateUserInfoQuery updateUserInfoQuery) {

        User user = userPort.updateUserInfo(
                User.updateUserInfo(
                        updateUserInfoQuery.getUuid(),
                        updateUserInfoQuery.getUsername(),
                        updateUserInfoQuery.getProfileImage(),
                        updateUserInfoQuery.getLocale(),
                        updateUserInfoQuery.getBio()
                ));

        return UpdateUserInfoDto.formUpdateUserInfoDto(user);
    }

    // 회원 탈퇴
    @Override
    public SoftDeleteUserDto softDeleteUser(SoftDeleteUserQuery softDeleteUserQuery) {

        User user = userPort.softDeleteUser(User.softDeleteUser(
                softDeleteUserQuery.getUuid()
        ));
        return SoftDeleteUserDto.formSoftDeleteUserDto(user);
    }

    // 다크모드 변경
    @Override
    public ToggleDarkModeDto toggleDarkMode(ToggleDarkModeQuery toggleDarkModeQuery) {

        User user = userPort.toggleDarkMode(User.toggleDarkMode(
                toggleDarkModeQuery.getUuid()
        ));
        return ToggleDarkModeDto.formToggleDarkModeDto(user);
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

    /**
     * 서버간 통신용 API들의 로직
     */
    // todo: 서버간 통신 API 사용하지 않을 경우에 삭제해야합니다.
    @Override
    public CheckCreatorDto checkCreator(CheckCreatorQuery checkCreatorQuery) {

        Boolean isCreator = userPort.checkCreator(User.checkCreator(checkCreatorQuery.getUuid()));
        return CheckCreatorDto.formCheckCreatorDto(isCreator);
    }
}
