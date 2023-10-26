package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateCreatorDto {

    private Boolean isCreator;
    private String categoryName;
    private String username;

    public static UpdateCreatorDto formUpdateCreatorDto(User user) {

        return UpdateCreatorDto.builder()
                .isCreator(user.getIsCreator())
                .categoryName(user.getCategory())
                .username(user.getUsername())
                .build();
    }
}
