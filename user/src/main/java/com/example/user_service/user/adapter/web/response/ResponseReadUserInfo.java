package com.example.user_service.user.adapter.web.response;

import com.example.user_service.user.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class ResponseReadUserInfo {

    private String username;
    private String profileImage;
    private String locale;
    private RoleType role;
    private String bio;
    private List<String> link;
    private Boolean darkMode;
    private Boolean isCreator;
    private String category;
}
