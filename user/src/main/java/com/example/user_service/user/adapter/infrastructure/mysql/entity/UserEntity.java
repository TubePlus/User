package com.example.user_service.user.adapter.infrastructure.mysql.entity;

import com.example.user_service.user.domain.LangType;
import com.example.user_service.user.domain.RoleType;
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

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private LangType language;

    @Column(nullable = false, name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(nullable = false, name ="soft_delete")
//    @Enumerated(EnumType.STRING)
    private Integer softDelete;

    @Column(name ="bio")
    private String bio;

    @Column(name ="link")
    private List link; // todo: 맞는지 확인

    @Column(nullable = false, name = "dark_mode")
    private Boolean darkMode;

    @Column(nullable = false, name = "is_creator")
    private Boolean isCreator;

    @Column(name = "category")
    private List category; // todo: 추후 변경? ArrayList<Enum> category로

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "uuid")
    private String uuid;

    public static UserEntity userToUserEntity(User user) {
        return UserEntity.builder()
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .language(user.getLanguage())
                .role(user.getRole())
                .softDelete(user.getSoftDelete())
                .bio(user.getBio())
                .link((List) user.getLink())
                .darkMode(user.getDarkMode())
                .isCreator(user.getIsCreator())
                .category((List) user.getCategory())
                .email(user.getEmail())
                .uuid(user.getUuid())
                .build();
    }
}
