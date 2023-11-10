package com.example.user_service.user.adapter.infrastructure.mysql.entity;

import com.example.user_service.user.domain.RoleType;
import com.example.user_service.user.domain.StatusType;
import com.example.user_service.user.domain.User;
import jakarta.persistence.*;
import org.hibernate.mapping.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "locale")
    private String locale;

    @Column(nullable = false, name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(nullable = false, name ="status")
    @Enumerated(EnumType.STRING)
    private StatusType status; // DELETED, ACTIVE, INACTIVE, BANNED, TEMPORAL_BAN

    @Column(name ="bio")
    private String bio;

    @Column(name ="link")
    private List link; // todo: 맞는지 확인

    @Column(nullable = false, name = "dark_mode")
    private Boolean darkMode;

    @Column(nullable = false, name = "is_creator")
    private Boolean isCreator;

    @Column(name = "category")
    private String category; // todo: 추후 변경? Enum category로

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "uuid")
    private String uuid;

    @Column(nullable = false, name = "youtube_handler")
    private String youtubeHandler;

    public static UserEntity userToUserEntity(User user) {
        return UserEntity.builder()
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .locale(user.getLocale())
                .role(user.getRole())
                .status(user.getStatus())
                .bio(user.getBio())
                .link((List) user.getLink())
                .darkMode(user.getDarkMode())
                .isCreator(user.getIsCreator())
                .category(user.getCategory())
                .email(user.getEmail())
                .uuid(user.getUuid())
                .youtubeHandler(user.getYoutubeHandler())
                .build();
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateYoutubeHandler(String youtubeHandler) {
        this.youtubeHandler = youtubeHandler;
    }

    public void registerCreator(String category) {
        this.isCreator = true;
        this.category = category;
    }

    public void updateCategory(String category) {
        this.category = category;
    }

    public void deleteCreator() {
        this.isCreator = false;
        this.category = null;
    }

    public void softDelete() {
        this.status = StatusType.DELETED;
    }

    public void toggleDarkMode() {
        this.darkMode = !this.darkMode;
    }

    public void comeBack() {
        this.status = StatusType.ACTIVE;
    }
}
