package com.example.user_service.user.application.service;

import com.example.user_service.user.application.ports.input.FavoritesUseCase;
import com.example.user_service.user.application.ports.output.FavoriteCreatorsPort;
import com.example.user_service.user.application.ports.output.dto.AddFavoriteDto;
import com.example.user_service.user.application.ports.output.dto.DeleteFavoriteDto;
import com.example.user_service.user.application.ports.output.dto.ReadFavoritesDto;
import com.example.user_service.user.domain.FavoriteCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteCreatorsService implements FavoritesUseCase {

    private final FavoriteCreatorsPort favoriteCreatorsPort;

    // 크리에이터 즐겨찾기 추가
    @Override
    public AddFavoriteDto addFavoriteCreator(AddFavoriteCreatorQuery addFavoriteCreatorQuery) {

        // 이미 즐겨찾기한 크리에이터인지 확인
        isFavoriteCreatorDuplicate(addFavoriteCreatorQuery.getCreatorUuid(), addFavoriteCreatorQuery.getUuid());

        // 아니라면 즐겨찾기 추가
        FavoriteCreator favoriteCreator = favoriteCreatorsPort.addFavoriteCreator(
                FavoriteCreator.addFavoriteCreator(
                        addFavoriteCreatorQuery.getCreatorUuid(),
                        addFavoriteCreatorQuery.getUuid()
                )
        );

        return AddFavoriteDto.formAddFavoriteDto(favoriteCreator);
    }

    // 즐겨찾기한 크리에이터 조회
    @Override
    public ReadFavoritesDto readFavoriteCreators(ReadFavoriteCreatorsQuery readFavoriteCreatorsQuery) {

        List<FavoriteCreator> favoriteCreatorList = favoriteCreatorsPort.readFavoriteCreators(
                FavoriteCreator.readFavoriteCreator(readFavoriteCreatorsQuery.getUuid()));

        return ReadFavoritesDto.formReadFavoritesDto(favoriteCreatorList);
    }

    // 크리에이터 즐겨찾기 삭제
    @Override
    public DeleteFavoriteDto deleteFavoriteCreator(DeleteFavoriteCreatorQuery deleteFavoriteCreatorQuery) {

        FavoriteCreator favoriteCreator = favoriteCreatorsPort.deleteFavoriteCreator(
                FavoriteCreator.deleteFavoriteCreator(
                deleteFavoriteCreatorQuery.getCreatorUuid(),
                deleteFavoriteCreatorQuery.getUuid()
        ));

        return DeleteFavoriteDto.formDeleteFavoriteDto(favoriteCreator);
    }

    // 기존 데이터 존재 여부 확인
    public void isFavoriteCreatorDuplicate(String creatorUuid, String uuid) {

        favoriteCreatorsPort.isFavoriteCreatorDuplicate(creatorUuid, uuid);
    }

}
