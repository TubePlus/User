package com.example.user_service.user.adapter.web.controller;

import com.example.user_service.user.adapter.web.request.*;
import com.example.user_service.user.adapter.web.response.*;
import com.example.user_service.user.application.ports.input.*;
import com.example.user_service.user.application.ports.output.dto.*;
import com.example.user_service.global.base.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") //todo: 임시설정
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserController {

    private final LogInUseCase logInUseCase;
    private final UsernameUseCase usernameUseCase;
    private final UserInfoUseCase userInfoUseCase;
    private final CreatorUseCase creatorUseCase;
    private final FavoritesUseCase favoritesUseCase;

    // request 받을 때 requestbody에 @Valid 사용해서 validation 체크.
    // uuid는 로그인/회원가입시에만 response로 프론트 단으로 return.

    /**
     * OAuth 로그인시 받을 수 있는 값들:
     * 1. id: 구글 OAuth로 받아오는 아이디 값(사용하지 않음)
     * 2. name: 구글의 유저 풀네임(사용하지 않음)
     * 3. email: 유저의 구글 이메일(사용)
     * 4. image: 유저의 구글 프로필 이미지(사용하지 않음)
     * 5. locale: 유저의 지역/언어(사용)
     */

    /**
     * 로그인 절차
     * 1. 프론트에서 OAuth를 통한 이메일을 백으로 던져준다.
     * 2. 백에서는 이메일을 통해 유저 정보를 조회한다.
     * 3. 유저 정보가 없다면 에러 return(프론트에서 회원가입 api 호출)
     * 4. 유저 정보가 있다면 로그인 완료(로그인 정보 return)
     * @param requestLoginUser Google OAuth를 통해 받은 id 값이 들어감
     * @return 로그인 후에 메인페이지로 redirect. 메인페이지에서 필요한 정보 return 해줌
     */
    //todo: 휴면회원인지 체크해서 휴면일 경우 복귀하는 로직도 추가해야함(재가입 로직도 추가해야함)
    // Redis에 request로 받은 토큰 값 저장도 해야함.
    // 토큰 검증 api/로직도 만들어야함.
    @Tag(name = "로그인/회원가입") @Operation(summary = "로그인")
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
                .bio(logInDto.getBio())
                .darkMode(logInDto.getDarkMode())
                .role(logInDto.getRole())
                .isCreator(logInDto.getIsCreator())
                .youtubeHandler(logInDto.getYoutubeHandler())
                .build();

        return ApiResponse.ofSuccess(responseLogInUser);
    }

    /**
     * 회원가입 절차
     * 1. 프론트에서 로그인 api호출
     * 2. 로그인 시도할 때 request로 던져준 email값이 DB에 없다면 회원가입 api 호출
     * 3. 회원가입 시에 request로 받은 값을 DB에 저장
     * 4. 회원가입 후에는 자동 로그인 처리
     * @param requestSignUpUser username(유저가 입력), token(유튜브 API용), email, locale(유저의 언어설정)
     * @return 로그인 시 return값과 같음
     */
    //todo: Redis에 request로 받은 토큰 값 저장도 해야함.
    @Tag(name = "로그인/회원가입") @Operation(summary = "회원가입")
    @PostMapping("signup")
    public ApiResponse<Object> signup(
            @Valid @RequestBody RequestSignUpUser requestSignUpUser) throws JsonProcessingException {

        SignUpDto signUpDto =
                logInUseCase.signUpUser(LogInUseCase.SignUpQuery.toQuery(requestSignUpUser));

        ResponseSignUpUser responseSignUpUser = ResponseSignUpUser.builder()
                .uuid(signUpDto.getUuid())
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .profileImage(signUpDto.getProfileImage())
                .locale(signUpDto.getLocale())
                .darkMode(signUpDto.getDarkMode())
                .role(signUpDto.getRole())
                .isCreator(signUpDto.getIsCreator())
                .youtubeHandler(signUpDto.getYoutubeHandler())
                .build();

        return ApiResponse.ofSuccess(responseSignUpUser);
    }

    @Tag(name = "로그인/회원가입") @Operation(summary = "유저네임 중복 여부 체크")
    @GetMapping("{username}/duplicate")
    public ApiResponse<Object> checkDuplicateUsername(@PathVariable String username) {

        IsDuplicateDto duplicateDto =
                usernameUseCase.checkDuplicateName(
                        UsernameUseCase.CheckDuplicateUsernameQuery.toQuery(username));

        ResponseIsDuplicate responseIsDuplicate = ResponseIsDuplicate.builder()
                .isDuplicate(duplicateDto.getIsDuplicate())
                .build();

        return ApiResponse.ofSuccess(responseIsDuplicate);
    }

    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "유저네임 변경")
    @PostMapping("username")
    public ApiResponse<Object> changeUsername(@Valid @RequestBody RequestChangeUsername requestChangeUsername) {

        ChangeUsernameDto changeUsernameDto =
                usernameUseCase.changeUsername(UsernameUseCase.ChangeUsernameQuery.toQuery(requestChangeUsername)
                );

        ResponseChangeUsername responseChangeUsername =
                ResponseChangeUsername.builder()
                        .username(changeUsernameDto.getUsername())
                        .build();

        return ApiResponse.ofSuccess(responseChangeUsername);
    }

    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "회원정보 조회(회원정보 불러오기)")
    @PostMapping("info")
    public ApiResponse<Object> getUserInfo(@Valid @RequestBody RequestReadUserInfo requestReadUserInfo) {

        ReadUserInfoDto readUserInfoDto =
                userInfoUseCase.getUserInfo(UserInfoUseCase.GetUserInfoQuery.toQuery(requestReadUserInfo));

        ResponseReadUserInfo responseReadUserInfo =
                ResponseReadUserInfo.builder()
                        .username(readUserInfoDto.getUsername())
                        .profileImage(readUserInfoDto.getProfileImage())
                        .locale(readUserInfoDto.getLocale())
                        .role(readUserInfoDto.getRole())
                        .bio(readUserInfoDto.getBio())
                        .link(readUserInfoDto.getLink())
                        .darkMode(readUserInfoDto.getDarkMode())
                        .isCreator(readUserInfoDto.getIsCreator())
                        .category(readUserInfoDto.getCategory())
                        .email(readUserInfoDto.getEmail())
                        .uuid(readUserInfoDto.getUuid())
                        .youtubeHandler(readUserInfoDto.getYoutubeHandler())
                        .build();

        return ApiResponse.ofSuccess(responseReadUserInfo);
    }

    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "회원정보 변경")
    @PutMapping("info")
    public ApiResponse<Object> updateUserInfo(@Valid @RequestBody RequestUpdateUserInfo requestUpdateUserInfo) {

        UpdateUserInfoDto updateUserInfoDto = userInfoUseCase.updateUserInfo(
                UserInfoUseCase.UpdateUserInfoQuery.toQuery(requestUpdateUserInfo));

        ResponseUpdateUserInfo responseUpdateUserInfo = ResponseUpdateUserInfo.builder()
                .email(updateUserInfoDto.getEmail())
                .username(updateUserInfoDto.getUsername())
                .profileImage(updateUserInfoDto.getProfileImage())
                .locale(updateUserInfoDto.getLocale())
                .bio(updateUserInfoDto.getBio())
                .darkMode(updateUserInfoDto.getDarkMode())
                .role(updateUserInfoDto.getRole())
                .isCreator(updateUserInfoDto.getIsCreator())
                .youtubeHandler(updateUserInfoDto.getYoutubeHandler())
                .build();

        return ApiResponse.ofSuccess(responseUpdateUserInfo);
    }

    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "다크모드 적용/해제")
    @PutMapping("darkmode")
    public ApiResponse<Object> updateDarkMode(@Valid @RequestBody RequestToggleDarkMode requestToggleDarkMode) {

        ToggleDarkModeDto toggleDarkModeDto = userInfoUseCase.toggleDarkMode(
                UserInfoUseCase.ToggleDarkModeQuery.toQuery(requestToggleDarkMode));

        ResponseToggleDarkMode responseToggleDarkMode = ResponseToggleDarkMode.builder()
                .darkMode(toggleDarkModeDto.getDarkMode())
                .build();

        return ApiResponse.ofSuccess(responseToggleDarkMode);
    }

    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "회원탈퇴")
    @PutMapping("softdelete")
    public ApiResponse<Object> softDelete(@Valid @RequestBody RequestSoftDeleteUser requestSoftDeleteUser) {

        SoftDeleteUserDto softDeleteUserDto
                = userInfoUseCase.softDeleteUser(
                        UserInfoUseCase.SoftDeleteUserQuery.toQuery(requestSoftDeleteUser));

        ResponseSoftDeleteUser responseSoftDeleteUser = ResponseSoftDeleteUser.builder()
                .email(softDeleteUserDto.getEmail())
                .username(softDeleteUserDto.getUsername())
                .status(softDeleteUserDto.getStatus())
                .build();

        return ApiResponse.ofSuccess(responseSoftDeleteUser);
    }

    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "휴면회원 복귀")
    @PutMapping("retrieve")
    public ApiResponse<Object> comeBack(
            @Valid @RequestBody RequestUserComeBack requestUserComeBack) throws JsonProcessingException {

        ComeBackDto comeBackDto = logInUseCase.comeBackUser(
                LogInUseCase.ComeBackQuery.toQuery(requestUserComeBack));

        ResponseComeBackUser responseComeBackUser = ResponseComeBackUser.builder()
                .uuid(comeBackDto.getUuid())
                .email(comeBackDto.getEmail())
                .username(comeBackDto.getUsername())
                .profileImage(comeBackDto.getProfileImage())
                .locale(comeBackDto.getLocale())
                .darkMode(comeBackDto.getDarkMode())
                .role(comeBackDto.getRole())
                .isCreator(comeBackDto.getIsCreator())
                .youtubeHandler(comeBackDto.getYoutubeHandler())
                .status(comeBackDto.getStatus())
                .build();

        return ApiResponse.ofSuccess(responseComeBackUser);
    }

    /**
     *
     * @param requestUpdateCreator 크리에이터 등록/크리에이터 수정 시에 같은 VO(requestUpdateCreator)사용함.
     * @return responseUpdateCreator 크리에이터 등록/크리에이터 수정 시에 같은 VO(responseUpdateCreator) 사용함.
     */
    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "크리에이터 전환")
    @PutMapping("creators/create")
    public ApiResponse<Object> registerCreator(@Valid @RequestBody RequestUpdateCreator requestUpdateCreator) {

        UpdateCreatorDto updateCreatorDto = creatorUseCase.registerCreator(
                CreatorUseCase.UpdateCreatorQuery.toQuery(requestUpdateCreator)
        );

        ResponseUpdateCreator responseUpdateCreator = ResponseUpdateCreator.builder()
                .username(updateCreatorDto.getUsername())
                .isCreator(updateCreatorDto.getIsCreator())
                .categoryName(updateCreatorDto.getCategoryName())
                .build();

        return ApiResponse.ofSuccess(responseUpdateCreator);
    }

    /**
     *
     * @param requestUpdateCreator 크리에이터 등록/크리에이터 수정 시에 같은 VO(requestUpdateCreator)사용함.
     * @return responseUpdateCreator 크리에이터 등록/크리에이터 수정 시에 같은 VO(responseUpdateCreator) 사용함.
     */
    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "크리에이터 카테고리 변경")
    @PutMapping("creators")
    public ApiResponse<Object> updateCreatorCategory(@Valid @RequestBody RequestUpdateCreator requestUpdateCreator) {

        UpdateCreatorDto updateCreatorDto = creatorUseCase.changeCreatorCategory(
                CreatorUseCase.UpdateCreatorQuery.toQuery(requestUpdateCreator)
        );

        ResponseUpdateCreator responseUpdateCreator = ResponseUpdateCreator.builder()
                .username(updateCreatorDto.getUsername())
                .isCreator(updateCreatorDto.getIsCreator())
                .categoryName(updateCreatorDto.getCategoryName())
                .build();

        return ApiResponse.ofSuccess(responseUpdateCreator);
    }

    @Tag(name = "회원정보 변경 및 조회") @Operation(summary = "크리에이터 등록 해제")
    @PutMapping("creators/rollback")
    public ApiResponse<Object> deleteCreator(@Valid @RequestBody RequestDeleteCreator requestDeleteCreator) {

        DeleteCreatorDto deleteCreatorDto = creatorUseCase.deleteCreator(
                CreatorUseCase.DeleteCreatorQuery.toQuery(requestDeleteCreator)
        );

        ResponseDeleteCreator responseDeleteCreator = ResponseDeleteCreator.builder()
                .username(deleteCreatorDto.getUsername())
                .isCreator(deleteCreatorDto.getIsCreator())
                .build();

        return ApiResponse.ofSuccess(responseDeleteCreator);
    }

    @Tag(name = "데이터 검색") @Operation(summary = "크리에이터 유저네임으로 검색(자동완성 검색)")
    @GetMapping("creators/{username}")
    public ApiResponse<Object> searchCreatorsByUsername(@PathVariable String username) {

        List<AutoSearchCreatorsDto> data =
                creatorUseCase.autoSearchCreators(CreatorUseCase.AutoSearchCreatorsQuery.toQuery(username));

        return ApiResponse.ofSuccess(data);
    }

    @Tag(name = "즐겨찾기 기능") @Operation(summary = "크리에이터 즐겨찾기 추가")
    @PostMapping("favorites/create")
    public ApiResponse<Object> getCreatorBookmarks(@Valid @RequestBody RequestAddFavorite requestAddFavorite) {

        AddFavoriteDto addFavoriteDto = favoritesUseCase.addFavoriteCreator(
                FavoritesUseCase.AddFavoriteCreatorQuery.toQuery(requestAddFavorite)
        );

        ResponseAddFavorite responseAddFavorite = ResponseAddFavorite.builder()
                .userUuid(addFavoriteDto.getUserUuid())
                .creatorUuid(addFavoriteDto.getCreatorUuid())
                .build();

        return ApiResponse.ofSuccess(responseAddFavorite);
    }

    //todo: 크리에이터 커뮤니티 정보도 넣어야함.
    @Tag(name = "즐겨찾기 기능") @Operation(summary = "즐겨찾는 크리에이터 조회")
    // 캐싱 적용 테스트
    @Cacheable(cacheNames = "favoriteCreators", key = "#requestReadFavorites.uuid")
    @PostMapping("favorites/view")
    public ApiResponse<Object> bookmarkCreator(@Valid @RequestBody RequestReadFavorites requestReadFavorites) {

        ReadFavoritesDto readFavoritesDto = favoritesUseCase.readFavoriteCreators(
                FavoritesUseCase.ReadFavoriteCreatorsQuery.toQuery(requestReadFavorites)
        );

        ResponseReadFavorites responseReadFavorites = ResponseReadFavorites.builder()
                .favoriteCreatorList(readFavoritesDto.getFavoriteCreatorList())
                .build();

        return ApiResponse.ofSuccess(responseReadFavorites);
    }
    
    @Tag(name = "즐겨찾기 기능") @Operation(summary = "크리에이터 즐겨찾기 삭제")
    @DeleteMapping("favorites")
    public ApiResponse<Object> deleteCreatorBookmark(@Valid @RequestBody RequestDeleteFavorite requestDeleteFavorite) {

        DeleteFavoriteDto deleteFavoriteDto = favoritesUseCase.deleteFavoriteCreator(
                FavoritesUseCase.DeleteFavoriteCreatorQuery.toQuery(requestDeleteFavorite)
        );

        ResponseDeleteFavorite responseDeleteFavorite = ResponseDeleteFavorite.builder()
                .userUuid(deleteFavoriteDto.getUserUuid())
                .creatorUuid(deleteFavoriteDto.getCreatorUuid())
                .build();

        return ApiResponse.ofSuccess(responseDeleteFavorite);
    }
}