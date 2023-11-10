package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.RoleType;
import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ReadUserInfoDto {

    private String username;
    private String profileImage;
    private String locale;
    private RoleType role;
    private String bio;
    private List<String> link;
    private Boolean darkMode;
    private Boolean isCreator;
    private String category;
    private String email;
    private String uuid;
    private String youtubeHandler;

    public static ReadUserInfoDto formUserInfoDto(User user) {

        return ReadUserInfoDto.builder()
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .locale(user.getLocale())
                .role(user.getRole())
                .bio(user.getBio())
                .link(user.getLink())
                .darkMode(user.getDarkMode())
                .isCreator(user.getIsCreator())
                .category(user.getCategory())
                .email(user.getEmail())
                .uuid(user.getUuid())
                .youtubeHandler(user.getYoutubeHandler())
                .build();
    }
}
