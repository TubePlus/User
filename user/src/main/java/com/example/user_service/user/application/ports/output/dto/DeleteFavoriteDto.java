package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.FavoriteCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteFavoriteDto {

    private String creatorUuid;
    private String userUuid;

    public static DeleteFavoriteDto formDeleteFavoriteDto(FavoriteCreator favoriteCreator) {

        return DeleteFavoriteDto.builder()
                .creatorUuid(favoriteCreator.getCreatorUuid())
                .userUuid(favoriteCreator.getUserUuid())
                .build();
    }
}
