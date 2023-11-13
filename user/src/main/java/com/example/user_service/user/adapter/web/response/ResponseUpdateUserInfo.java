package com.example.user_service.user.adapter.web.response;

import com.example.user_service.user.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUpdateUserInfo {

    private String email;
    private String username;
    private String profileImage;
    private String locale;
    private String bio;
    private Boolean darkMode;
    private RoleType role;
    private Boolean isCreator;
    private String youtubeHandler;
}
