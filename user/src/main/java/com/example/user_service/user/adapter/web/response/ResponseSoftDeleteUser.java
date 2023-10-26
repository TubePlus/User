package com.example.user_service.user.adapter.web.response;

import com.example.user_service.user.domain.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class ResponseSoftDeleteUser {

    private String email;
    private String username;
    private StatusType status;
}
