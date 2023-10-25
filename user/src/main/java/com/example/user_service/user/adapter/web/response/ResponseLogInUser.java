package com.example.user_service.user.adapter.web.response;

import com.example.user_service.user.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class ResponseLogInUser {

    private String uuid;
    private String email;
    private String username;
    private String profileImage;
    private String locale;
    private Boolean darkMode;
    private RoleType role;
    private Boolean isCreator;
}
