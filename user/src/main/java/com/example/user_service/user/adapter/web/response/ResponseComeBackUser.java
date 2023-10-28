package com.example.user_service.user.adapter.web.response;

import com.example.user_service.user.domain.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ResponseComeBackUser {

    private String email;
    private String username;
    private StatusType status;
}
