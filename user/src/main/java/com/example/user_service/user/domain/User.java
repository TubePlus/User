package com.example.user_service.user.domain;

import com.example.user_service.user.adapter.infrastructure.mysql.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String profileImage;
    private LangType language;
    private RoleType role;
    private Integer softDelete;
    private String bio;
    private List link;
    private Boolean darkMode;
    private Boolean isCreator;
    private List category;
    private String email;
    private String uuid;

    public static User logInUser(String email) {
        return User.builder()
                .email(email)
                .build();
    }

    public static User signUpUser(
            String username, String profileImage, RoleType role, Boolean isCreator,
            String email, String uuid, LangType language, Boolean darkMode, Integer softDelete) {
        return User.builder()
                .username(username)
                .profileImage(profileImage)
                .role(role)
                .isCreator(isCreator)
                .email(email)
                .uuid(uuid)
                .language(language)
                .darkMode(darkMode)
                .softDelete(softDelete)
                .build();
    }

    public static User userEntityToUser(UserEntity userEntity) {
        return User.builder()
                .username(userEntity.getUsername())
                .profileImage(userEntity.getProfileImage())
                .language(userEntity.getLanguage())
                .role(userEntity.getRole())
                .softDelete(userEntity.getSoftDelete())
                .bio(userEntity.getBio())
                .link((List) userEntity.getLink())
                .darkMode(userEntity.getDarkMode())
                .isCreator(userEntity.getIsCreator())
                .category((List) userEntity.getCategory())
                .email(userEntity.getEmail())
                .uuid(userEntity.getUuid())
                .build();
    }
}
