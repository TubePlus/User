package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.FavoriteCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ReadFavoritesDto {

    private List<FavoriteCreator> favoriteCreatorList;

    public static ReadFavoritesDto formReadFavoritesDto(List<FavoriteCreator> favoriteCreatorList) {

        return ReadFavoritesDto.builder()
                .favoriteCreatorList(favoriteCreatorList)
                .build();
    }
}
