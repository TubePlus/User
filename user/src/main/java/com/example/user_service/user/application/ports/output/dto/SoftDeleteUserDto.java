package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.StatusType;
import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SoftDeleteUserDto {

    private String email;
    private String username;
    private StatusType status;

    public static SoftDeleteUserDto formSoftDeleteUserDto(User user) {

        return SoftDeleteUserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .status(user.getStatus())
                .build();
    }
}
