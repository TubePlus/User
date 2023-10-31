package com.example.user_service.user.application.ports.output;

import com.example.user_service.user.domain.FavoriteCreator;

import java.util.List;

public interface FavoriteCreatorsPort {

    FavoriteCreator addFavoriteCreator(FavoriteCreator favoriteCreator);
    List<FavoriteCreator> readFavoriteCreators(FavoriteCreator favoriteCreator);
    FavoriteCreator deleteFavoriteCreator(FavoriteCreator favoriteCreator);
    void isFavoriteCreatorDuplicate(String creatorUuid, String uuid);
}