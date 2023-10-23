package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChangeUsernameDto {

    private String username;

    public static ChangeUsernameDto formUsernameDto(User user) {
        return new ChangeUsernameDto(user.getUsername());
    }
}
