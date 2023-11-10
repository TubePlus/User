package com.example.user_service.user.adapter.web.response;

import com.example.user_service.user.domain.RoleType;
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

    private String uuid;
    private String email;
    private String username;
    private String profileImage;
    private String locale;
    private Boolean darkMode;
    private RoleType role;
    private Boolean isCreator;
    private String youtubeHandler;
    private StatusType status;
}
