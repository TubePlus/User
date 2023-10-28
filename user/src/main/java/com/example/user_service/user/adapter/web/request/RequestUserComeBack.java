package com.example.user_service.user.adapter.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserComeBack {

    @NotNull
    private String email;

    @NotNull
    private String token;
}
