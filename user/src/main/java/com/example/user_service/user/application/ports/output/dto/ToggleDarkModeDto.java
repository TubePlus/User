package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ToggleDarkModeDto {

    private Boolean darkMode;

    public static ToggleDarkModeDto formToggleDarkModeDto(User user) {

        return ToggleDarkModeDto.builder()
                .darkMode(user.getDarkMode())
                .build();
    }
}
