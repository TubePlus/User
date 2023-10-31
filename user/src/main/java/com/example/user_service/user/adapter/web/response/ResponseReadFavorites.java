package com.example.user_service.user.adapter.web.response;

import com.example.user_service.user.domain.FavoriteCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class ResponseReadFavorites {

    private List<FavoriteCreator> favoriteCreatorList;
}
