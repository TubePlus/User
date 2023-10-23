package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.LangType;
import com.example.user_service.user.domain.RoleType;
import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto {

    private String uuid; //todo: uuid 계속 보내줘야하는지 팀원들이랑 얘기해보고 지우기
    private String email;
    private String username;
    private String profileImage;
    private LangType language;
    private String bio;
    private Boolean darkMode;
    private RoleType role;
    private Boolean isCreator;

    public static UserInfoDto formUserInfoDto(User user) {

        return UserInfoDto.builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .language(user.getLanguage())
                .bio(user.getBio())
                .darkMode(user.getDarkMode())
                .role(user.getRole())
                .isCreator(user.getIsCreator())
                .build();
    }
}
