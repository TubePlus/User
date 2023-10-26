package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteCreatorDto {

    private String username;
    private Boolean isCreator;

    public static DeleteCreatorDto formDeleteCreatorDto(User user) {

        return DeleteCreatorDto.builder()
                .username(user.getUsername())
                .isCreator(user.getIsCreator())
                .build();
    }
}
