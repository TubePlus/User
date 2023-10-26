package com.example.user_service.user.adapter.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class RequestSignUpUser {

    @NotNull
    private String username; // username은 유튜브 api로 받아오는 것이 아닌 회원가입 단계에서 직접 입력받아야 함.

    @NotNull
    private String email; // Google OAuth를 통해 받아온 값을 프론트에서 넘겨줌

    @NotNull
    private String token;

    private String locale; // Google OAuth를 통해 받아온 값을 프론트에서 넘겨줌
}
