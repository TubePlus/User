package com.example.user_service.user.application.ports.output.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AutoSearchCreatorsDto {

    private String creatorUuid;
    private String username;
    private String category;
    private String profileImage;
    private String bio;
}
