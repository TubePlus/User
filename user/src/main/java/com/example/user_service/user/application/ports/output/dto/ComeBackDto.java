package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.RoleType;
import com.example.user_service.user.domain.StatusType;
import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ComeBackDto {

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

    public static ComeBackDto formComeBackDto(User user) {

        return ComeBackDto.builder()
                .uuid(user.getUuid())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .locale(user.getLocale())
                .darkMode(user.getDarkMode())
                .role(user.getRole())
                .isCreator(user.getIsCreator())
                .youtubeHandler(user.getYoutubeHandler())
                .status(user.getStatus())
                .build();
    }
}
