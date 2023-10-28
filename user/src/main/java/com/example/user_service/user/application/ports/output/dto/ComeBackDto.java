package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.StatusType;
import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ComeBackDto {

    private String email;
    private String username;
    private StatusType status;

    public static ComeBackDto formComeBackDto(User user) {

        return ComeBackDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .status(user.getStatus())
                .build();
    }
}
