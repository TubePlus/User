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
    private String locale;
    private RoleType role;
    private StatusType status; // DELETED, ACTIVE, INACTIVE, BANNED, TEMPORAL_BAN
    private String bio;
    private List link;
    private Boolean darkMode;
    private Boolean isCreator;
    private String category; //todo: 카테고리 ENUM 타입으로 변경하기
    private String email;
    private String uuid;

    public static User logInUser(String email, String profileImage) {

        return User.builder()
                .email(email)
                .profileImage(profileImage)
                .build();
    }

    public static User signUpUser(String username, String profileImage, RoleType role, Boolean isCreator,
            String email, String uuid, String locale, Boolean darkMode, StatusType status) {

        return User.builder()
                .username(username)
                .profileImage(profileImage)
                .role(role)
                .isCreator(isCreator)
                .email(email)
                .uuid(uuid)
                .locale(locale)
                .darkMode(darkMode)
                .status(status)
                .build();
    }

    public static User comeBackUser(String email, String profileImage) {

        return User.builder()
                .email(email)
                .profileImage(profileImage)
                .build();
    }

    public static User changeUsername(String username, String uuid) {

        return User.builder()
                .username(username)
                .uuid(uuid)
                .build();
    }

    public static User checkCreator(String uuid) {

        return User.builder()
                .uuid(uuid)
                .build();
    }

    public static User updateCreator(String uuid, String categoryName) {

        return User.builder()
                .uuid(uuid)
                .category(categoryName)
                .build();
    }

    public static User deleteCreator(String uuid) {

        return User.builder()
                .uuid(uuid)
                .build();
    }

    public static User softDeleteUser(String uuid) {

        return User.builder()
                .uuid(uuid)
                .build();
    }

    public static User toggleDarkMode(String uuid) {

        return User.builder()
                .uuid(uuid)
                .build();
    }

    public static User getUserInfo(String uuid) {

        return User.builder()
                .uuid(uuid)
                .build();
    }

    public static User autoSearchCreators(String q) {

        return User.builder()
                .username(q)
                .build();
    }

    public static User userEntityToUser(UserEntity userEntity) {

        return User.builder()
                .username(userEntity.getUsername())
                .profileImage(userEntity.getProfileImage())
                .locale(userEntity.getLocale())
                .role(userEntity.getRole())
                .status(userEntity.getStatus())
                .bio(userEntity.getBio())
                .link((List) userEntity.getLink())
                .darkMode(userEntity.getDarkMode())
                .isCreator(userEntity.getIsCreator())
                .category(userEntity.getCategory())
                .email(userEntity.getEmail())
                .uuid(userEntity.getUuid())
                .build();
    }
}
