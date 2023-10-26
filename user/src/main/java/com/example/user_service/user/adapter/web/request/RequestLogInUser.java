package com.example.user_service.user.adapter.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class RequestLogInUser {

    @NotNull
    private String email; // Google OAuth를 통해 받은 이메일 값

    @NotNull
    private String token; // Youtube Api를 사용하기 위한 토큰 값(redis에 저장)
}
