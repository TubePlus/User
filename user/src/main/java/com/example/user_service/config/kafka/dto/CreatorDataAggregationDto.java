package com.example.user_service.config.kafka.dto;

import lombok.Data;

@Data
public class CreatorDataAggregationDto {
    private String userUuid;
    private String category;
    private String profileImage;
    private String youtubeHandler;
    private String userName;

}
