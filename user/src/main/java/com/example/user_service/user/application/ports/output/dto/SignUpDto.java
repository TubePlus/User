package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.RoleType;
import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignUpDto {

    private String uuid;
    private String email;
    private String username;
    private String profileImage;
    private String locale;
    private Boolean darkMode;
    private RoleType role;
    private Boolean isCreator;

    // return 값을 담는 Dto
    public static SignUpDto formSignUpDto(User user) {
        return SignUpDto.builder()
                .uuid(user.getUuid())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .locale(user.getLocale())
                .darkMode(user.getDarkMode())
                .role(user.getRole())
                .isCreator(user.getIsCreator())
                .build();
    }
}
