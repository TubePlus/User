package com.example.user_service.user.adapter.infrastructure.mysql.entity;

import com.example.user_service.user.domain.FavoriteCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "favorite_creators")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteCreatorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_uuid", nullable = false)
    private String creatorUuid;

    @Column(name ="user_uuid", nullable = false)
    private String userUuid;

    public static FavoriteCreatorsEntity favDomainToEntity(FavoriteCreator favoriteCreator) {

        return FavoriteCreatorsEntity.builder()
                .creatorUuid(favoriteCreator.getCreatorUuid())
                .userUuid(favoriteCreator.getUserUuid())
                .build();
    }


}