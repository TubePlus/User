package com.example.user_service.config.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatorDataAggregationDto {
    private String userUuid;
    private String category;
    private String profileImage;
    private String youtubeHandler;
    private String userName;
}
