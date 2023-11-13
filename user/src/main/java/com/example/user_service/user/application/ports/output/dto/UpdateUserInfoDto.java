package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.RoleType;
import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoDto {

    private String email;
    private String username;
    private String profileImage;
    private String locale;
    private String bio;
    private Boolean darkMode;
    private RoleType role;
    private Boolean isCreator;
    private String youtubeHandler;

    public static UpdateUserInfoDto formUpdateUserInfoDto(User user) {

        return UpdateUserInfoDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .locale(user.getLocale())
                .bio(user.getBio())
                .darkMode(user.getDarkMode())
                .role(user.getRole())
                .isCreator(user.getIsCreator())
                .youtubeHandler(user.getYoutubeHandler())
                .build();
    }
}
