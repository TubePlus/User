package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestAddFavorite;
import com.example.user_service.user.adapter.web.request.RequestDeleteFavorite;
import com.example.user_service.user.adapter.web.request.RequestReadFavorites;
import com.example.user_service.user.application.ports.output.dto.AddFavoriteDto;
import com.example.user_service.user.application.ports.output.dto.DeleteFavoriteDto;
import com.example.user_service.user.application.ports.output.dto.ReadFavoritesDto;
import lombok.Builder;
import lombok.Getter;

public interface FavoritesUseCase {

    AddFavoriteDto addFavoriteCreator(AddFavoriteCreatorQuery addFavoriteCreatorQuery);
    ReadFavoritesDto readFavoriteCreators(ReadFavoriteCreatorsQuery readFavoriteCreatorsQuery);
    DeleteFavoriteDto deleteFavoriteCreator(DeleteFavoriteCreatorQuery deleteFavoriteCreatorQuery);

    @Getter
    @Builder
    class AddFavoriteCreatorQuery {

        private String uuid;
        private String creatorUuid;

        public static AddFavoriteCreatorQuery toQuery(RequestAddFavorite requestAddFavorite) {

            return AddFavoriteCreatorQuery.builder()
                    .uuid(requestAddFavorite.getUuid())
                    .creatorUuid(requestAddFavorite.getCreatorUuid())
                    .build();
        }
    }

    @Getter
    @Builder
    class ReadFavoriteCreatorsQuery {

        private String uuid;

        public static ReadFavoriteCreatorsQuery toQuery(RequestReadFavorites requestReadFavorites) {

            return ReadFavoriteCreatorsQuery.builder()
                    .uuid(requestReadFavorites.getUuid())
                    .build();
        }
    }

    @Getter
    @Builder
    class DeleteFavoriteCreatorQuery {

        private String uuid;
        private String creatorUuid;

        public static DeleteFavoriteCreatorQuery toQuery(RequestDeleteFavorite requestDeleteFavorite) {

            return DeleteFavoriteCreatorQuery.builder()
                    .uuid(requestDeleteFavorite.getUuid())
                    .creatorUuid(requestDeleteFavorite.getCreatorUuid())
                    .build();
        }
    }
}
