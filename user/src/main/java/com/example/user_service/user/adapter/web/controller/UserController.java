package com.example.user_service.user.adapter.web.controller;

import com.example.user_service.user.adapter.web.request.RequestLogInUser;
import com.example.user_service.user.adapter.web.request.RequestSignUpUser;
import com.example.user_service.user.adapter.web.response.ResponseDuplicateCheck;
import com.example.user_service.user.adapter.web.response.ResponseLogInUser;
import com.example.user_service.user.adapter.web.response.ResponseSignUpUser;
import com.example.user_service.user.application.ports.input.DuplicateCheckUseCase;
import com.example.user_service.user.application.ports.input.LogInUseCase;
import com.example.user_service.user.application.ports.input.SignUpUseCase;
import com.example.user_service.user.application.ports.input.UserInfoUseCase;
import com.example.user_service.user.application.ports.output.dto.DuplicateCheckDto;
import com.example.user_service.user.application.ports.output.dto.LogInDto;
import com.example.user_service.user.application.ports.output.dto.SignUpDto;
import com.example.user_service.user.application.ports.output.dto.UserInfoDto;
import com.example.user_service.global.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserController {

    private final LogInUseCase logInUseCase;
    private final SignUpUseCase signUpUseCase;
    private final DuplicateCheckUseCase duplicateCheckUseCase;
    private final UserInfoUseCase userInfoUseCase;

    //todo: ❗나중에 return 값 api response로 모두 수정해줘야 함.
    //todo: request 받을 때 requestbody에 @Valid 사용해서 validation 체크해줘야 함.
    //todo: ⚠️uuid는 로그인/회원가입시에만 response로 프론트 단으로 return 해주므로 웬만해서는 다른 api를 통해서는 자제.

    /**
     * OAuth 로그인시 받을 수 있는 값들:
     * 1. id: 구글 OAuth로 받아오는 아이디 값 -> 이 값이 유저마다 고유하기 때문에 uuid 값으로 대체해서 사용.
     * 2. name: 구글의 유저 풀네임
     * 3. email: 유저의 구글 이메일(사용)
     * 4. image: 유저의 구글 프로필 이미지(사용)
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
    @PostMapping("login")
    public ApiResponse<Object> login(@Valid @RequestBody RequestLogInUser requestLoginUser) {

        LogInDto logInDto = logInUseCase.logInUser(LogInUseCase.LogInQuery.toQuery(requestLoginUser));
        ResponseLogInUser responseLogInUser = ResponseLogInUser.builder()
                .uuid(logInDto.getUuid())
                .email(logInDto.getEmail())
                .username(logInDto.getUsername())
                .profileImage(logInDto.getProfileImage())
                .language(logInDto.getLanguage())
                .darkMode(logInDto.getDarkMode())
                .role(logInDto.getRole())
                .isCreator(logInDto.getIsCreator())
                .build();
        return ApiResponse.ofSuccess(responseLogInUser);
    }

    /**
     * 회원가입 절차
     * 1. 프론트에서 로그인 시도
     * 2. 로그인 시도할 때 request로 던져준 email값이 DB에 없다면 회원가입 api 호출
     * 3. 회원가입 시에 request로 받은 값을 DB에 저장
     * 4. 회원가입 후에는 자동 로그인 처리
     * @param requestSignUpUser username(유저가 입력), profileImage(Youtube API로 받는 값) / email, uuid(구글 OAuth로 받는 값)
     * @return 로그인 시 return값과 같음
     */
    //todo: language default값을 null로 설정
    //todo: language -> locale로 네이밍 변경
    @PostMapping("signup")
    public ApiResponse<Object> signup(@Valid @RequestBody RequestSignUpUser requestSignUpUser) {

        SignUpDto signUpDto =
                signUpUseCase.signUpUser(SignUpUseCase.SignUpQuery.toQuery(requestSignUpUser));
        ResponseSignUpUser responseSignUpUser = ResponseSignUpUser.builder()
                .uuid(signUpDto.getUuid())
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .profileImage(signUpDto.getProfileImage())
                .language(signUpDto.getLanguage())
                .darkMode(signUpDto.getDarkMode())
                .role(signUpDto.getRole())
                .isCreator(signUpDto.getIsCreator())
                .build();
        return ApiResponse.ofSuccess(responseSignUpUser);
    }

    // 회원가입

    // 유저네임 중복 체크(기존 유저네임 변경에서)
    @GetMapping("{username}/duplicate")
    public ApiResponse<Object> checkDuplicateUsername(@PathVariable String username) {

        DuplicateCheckDto checkDto =
                duplicateCheckUseCase.checkDuplicateUsername(
                        DuplicateCheckUseCase.CheckUsernameQuery.toQuery(username)
                );
        ResponseDuplicateCheck responseDuplicateCheck =
                ResponseDuplicateCheck.builder()
                        .isDuplicate(checkDto.getIsDuplicate())
                        .build();
        return ApiResponse.ofSuccess(responseDuplicateCheck);
    }

    // Google OAuth ID 중복 체크(가입 여부 조회를 위한)
    @GetMapping("{oauthId}/duplicate")
    public ApiResponse<Object> checkDuplicateOauthId(@PathVariable String oauthId) {

        DuplicateCheckDto checkDto =
                duplicateCheckUseCase.checkDuplicateGoogleAuth(
                        DuplicateCheckUseCase.CheckGoogleAuthQuery.toQuery(oauthId)
                );
        ResponseDuplicateCheck responseDuplicateCheck =
                ResponseDuplicateCheck.builder()
                        .isDuplicate(checkDto.getIsDuplicate())
                        .build();
        return ApiResponse.ofSuccess(responseDuplicateCheck);
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

    @PutMapping("creators/create") // 일반 유저 크리에이터로 등록
    public void registerCreator(/*request*/) {

    }

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