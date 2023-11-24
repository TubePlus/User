package com.example.user_service.user.domain;

import com.example.user_service.user.adapter.infrastructure.mysql.entity.FavoriteCreatorsEntity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorite_creator")
public class FavoriteCreator {

    // todo: 커뮤니티 정보도 나중에 야무지게 추가하기~
    private String userUuid;
    private String creatorUuid;
    private String creatorName;
    private String creatorProfileImage;
    private String creatorBio;
    private String creatorCategory;

    public static FavoriteCreator addFavoriteCreator(String creatorUuid, String uuid) {

        return FavoriteCreator.builder()
                .creatorUuid(creatorUuid)
                .userUuid(uuid)
                .build();
    }

    public static FavoriteCreator readFavoriteCreator(String uuid) {

        return FavoriteCreator.builder()
                .userUuid(uuid)
                .build();
    }

    public static FavoriteCreator deleteFavoriteCreator(String creatorUuid, String uuid) {

        return FavoriteCreator.builder()
                .creatorUuid(creatorUuid)
                .userUuid(uuid)
                .build();
    }

    public static FavoriteCreator favEntityToDomain(FavoriteCreatorsEntity favoriteCreatorsEntity) {

        return FavoriteCreator.builder()
                .creatorUuid(favoriteCreatorsEntity.getCreatorUuid())
                .userUuid(favoriteCreatorsEntity.getUserUuid())
                .build();
    }
}
