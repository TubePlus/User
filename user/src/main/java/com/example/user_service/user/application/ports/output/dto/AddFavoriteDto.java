package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.FavoriteCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddFavoriteDto {

    private String userUuid;
    private String creatorUuid;

    public static AddFavoriteDto formAddFavoriteDto(FavoriteCreator favoriteCreator) {
        return AddFavoriteDto.builder()
                .userUuid(favoriteCreator.getUserUuid())
                .creatorUuid(favoriteCreator.getCreatorUuid())
                .build();
    }
}
