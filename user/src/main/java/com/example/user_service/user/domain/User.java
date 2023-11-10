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
    private String bio; // todo: 명시적인걸로 바꾸기 알아보기 쉽게
    private List link; // todo: 따로 테이블로 빼기
    private Boolean darkMode; // todo: 컨벤션 통일? -> isDarkMode
    private Boolean isCreator;
    private String category; //todo: 카테고리 ENUM 타입으로 변경하기
    private String email;
    private String uuid;
    private String youtubeHandler; // 유튜브 핸들러 (@your-handler)

    public static User logInUser(String email, String profileImage, String youtubeHandler) {

        return User.builder()
                .email(email)
                .profileImage(profileImage)
                .youtubeHandler(youtubeHandler)
                .build();
    }

    public static User signUpUser(String username, String profileImage, String youtubeHandler,
                                  RoleType role, Boolean isCreator, String email, String uuid,
                                  String locale, Boolean darkMode, StatusType status) {

        return User.builder()
                .username(username)
                .profileImage(profileImage)
                .youtubeHandler(youtubeHandler)
                .role(role)
                .isCreator(isCreator)
                .email(email)
                .uuid(uuid)
                .locale(locale)
                .darkMode(darkMode)
                .status(status)
                .build();
    }

    public static User comeBackUser(String email, String profileImage, String youtubeHandler) {

        return User.builder()
                .email(email)
                .profileImage(profileImage)
                .youtubeHandler(youtubeHandler)
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
                .youtubeHandler(userEntity.getYoutubeHandler())
                .build();
    }
}
