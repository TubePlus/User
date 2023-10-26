package com.example.user_service.user.adapter.web.controller;

import com.example.user_service.user.adapter.web.request.RequestChangeUsername;
import com.example.user_service.user.adapter.web.request.RequestLogInUser;
import com.example.user_service.user.adapter.web.request.RequestSignUpUser;
import com.example.user_service.user.adapter.web.response.ResponseChangeUsername;
import com.example.user_service.user.adapter.web.response.ResponseIsDuplicate;
import com.example.user_service.user.adapter.web.response.ResponseLogInUser;
import com.example.user_service.user.adapter.web.response.ResponseSignUpUser;
import com.example.user_service.user.application.ports.input.*;
import com.example.user_service.user.application.ports.output.dto.*;
import com.example.user_service.global.base.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserController {

    private final LogInUseCase logInUseCase;
    private final SignUpUseCase signUpUseCase;
    private final ChangeUsernameUseCase changeUsernameUseCase;
    private final UserInfoUseCase userInfoUseCase;
    private final DuplicateUsernameUseCase duplicateUsernameUseCase;

    //todo: return 값 api response로 모두 수정해줘야 함.
    // 프론트에서 youtube data api 사용 위한 토큰 값을 request로 받아오는 방향으로 수정.
    // 데이터 던져주고 받아줄 때 jwt 사용해서 해줘야 함.

    // request 받을 때 requestbody에 @Valid 사용해서 validation 체크해줘야 함.uuid
    // ⚠️uuid는 로그인/회원가입시에만 response로 프론트 단으로 return 해주므로 웬만해서는 다른 api를 통해서는 자제.

    /**
     * OAuth 로그인시 받을 수 있는 값들:
     * 1. id: 구글 OAuth로 받아오는 아이디 값(사용하지 않음)
     * 2. name: 구글의 유저 풀네임(사용하지 않음)
     * 3. email: 유저의 구글 이메일(사용)
     * 4. image: 유저의 구글 프로필 이미지(사용하지 않음)
     */

    // 로그인
    /**
     * 로그인 절차
     * 1. 프론트에서 OAuth를 통한 이메일을 백으로 던져준다.
     * 2. 백에서는 이메일을 통해 유저 정보를 조회한다.
     * 3. 유저 정보가 없다면 커스텀 에러 return(프론트에서 회원가입 api 호출할 것임)
     * 4. 유저 정보가 있다면 로그인 완료(로그인 정보 return)
     * @param requestLoginUser Google OAuth를 통해 받은 id 값이 들어감
     * @return 로그인 후에 메인페이지로 redirect. 메인페이지에서 필요한 정보 return 해줌
     */
    //todo: 휴면회원인지 체크해서 휴면일 경우 복귀하는 로직도 추가해야함
    // Redis에 request로 받은 토큰 값 저장도 해야함.
    // 토큰 검증 api/로직도 만들어야함.
    @PostMapping("login")
    public ApiResponse<Object> login(
            @Valid @RequestBody RequestLogInUser requestLoginUser) throws JsonProcessingException {

        LogInDto logInDto = logInUseCase.logInUser(LogInUseCase.LogInQuery.toQuery(requestLoginUser));
        ResponseLogInUser responseLogInUser = ResponseLogInUser.builder()
                .uuid(logInDto.getUuid())
                .email(logInDto.getEmail())
                .username(logInDto.getUsername())
                .profileImage(logInDto.getProfileImage())
                .locale(logInDto.getLocale())
                .darkMode(logInDto.getDarkMode())
                .role(logInDto.getRole())
                .isCreator(logInDto.getIsCreator())
                .build();
        return ApiResponse.ofSuccess(responseLogInUser);
    }

    // 회원가입
    /**
     * 회원가입 절차
     * 1. 프론트에서 로그인 api호출
     * 2. 로그인 시도할 때 request로 던져준 email값이 DB에 없다면 회원가입 api 호출
     * 3. 회원가입 시에 request로 받은 값을 DB에 저장
     * 4. 회원가입 후에는 자동 로그인 처리
     * @param requestSignUpUser username(유저가 입력), profileImage(Youtube API로 받는 값) / email, uuid(구글 OAuth로 받는 값)
     * @return 로그인 시 return값과 같음
     */
    //todo: Redis에 request로 받은 토큰 값 저장도 해야함.
    // 임시로 생성한 uuid값 생성하는 로직 추가해야함.
    @PostMapping("signup")
    public ApiResponse<Object> signup(
            @Valid @RequestBody RequestSignUpUser requestSignUpUser) throws JsonProcessingException {

        SignUpDto signUpDto =
                signUpUseCase.signUpUser(SignUpUseCase.SignUpQuery.toQuery(requestSignUpUser));
        ResponseSignUpUser responseSignUpUser = ResponseSignUpUser.builder()
                .uuid(signUpDto.getUuid())
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .profileImage(signUpDto.getProfileImage())
                .locale(signUpDto.getLocale())
                .darkMode(signUpDto.getDarkMode())
                .role(signUpDto.getRole())
                .isCreator(signUpDto.getIsCreator())
                .build();
        return ApiResponse.ofSuccess(responseSignUpUser);
    }

    // 회원가입 시 유저네임 중복 여부 체크
    @GetMapping("users/{username}/duplicate")
    public ApiResponse<Object> checkDuplicateUsername(@PathVariable String username) {

        IsDuplicateDto duplicateDto =
                duplicateUsernameUseCase.checkDuplicateName(
                        DuplicateUsernameUseCase.CheckDuplicateUsernameQuery.toQuery(username));
        ResponseIsDuplicate responseIsDuplicate = ResponseIsDuplicate.builder()
                .isDuplicate(duplicateDto.getIsDuplicate())
                .build();
        return ApiResponse.ofSuccess(responseIsDuplicate);
    }

    // 유저네임 변경
    @PostMapping("username")
    public ApiResponse<Object> changeUsername(@Valid @RequestBody RequestChangeUsername requestChangeUsername) {

        ChangeUsernameDto changeUsernameDto =
                changeUsernameUseCase.changeUsername(ChangeUsernameUseCase.ChangeUsernameQuery.toQuery(
                        requestChangeUsername.getUsername(), requestChangeUsername.getUuid())
                );
        ResponseChangeUsername responseChangeUsername =
                ResponseChangeUsername.builder()
                        .username(changeUsernameDto.getUsername())
                        .build();
        return ApiResponse.ofSuccess(responseChangeUsername);
    }

    // 회원 정보 불러오기
    @GetMapping("{uuid}/info")
    public void getUserInfo(@PathVariable String uuid) {

        UserInfoDto userInfoDto = userInfoUseCase.getUserInfo(UserInfoUseCase.UserInfoQuery.toQuery(uuid));

    }

    @PutMapping("info")
    public void updateUserInfo(/*request*/) {

    }

    @PutMapping("darkmode")
    public void updateDarkMode(/*request*/) {

    }

    @PutMapping("softdelete")
    public void softDelete(/*request*/) {

    }

    @PutMapping("retrieve") // 회원 복귀
    public void comeBack(/*request*/) {

    }

    // 일반 유저 크리에이터로 전환
//    @PutMapping("creators/create")
//    public void registerCreator(RequestRegisterCreator requestRegisterCreator) {
//
//        RegisterCreatorDto registerCreatorDto =
//    }


    @PutMapping("creators")
    public void updateCreatorCategory(/*request*/) {

    }

    @PutMapping("creators/rollback")
    public void deleteCreator(/*request*/) {

    }

    @GetMapping("{uuid}/creatorBookmarks")
    public void getCreatorBookmarks(@PathVariable String uuid) {

    }

    @PostMapping("creatorBookmarks")
    public void bookmarkCreator(/*request*/) {

    }

    @DeleteMapping("creatorBookmarks")
    public void deleteCreatorBookmark(/*request*/) {

    }

    /**
     * BE APIs
     */
    //todo: return 값이 uuid여야함.

    @GetMapping("{username}/uuid")
    public void getUuidByUsername(@PathVariable String username) {

    }

}