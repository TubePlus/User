package com.example.user_service.config.kafka.dto;

public class CreatorDataAggregationDto {
    private String userUuid;
    private String category;
    private String profileImage;
    private String youtubeHandler;
    private String userName;

    public CreatorDataAggregationDto(String uuid, String category, String profileImage, String youtubeHandler, String userName) {
        this.userUuid = uuid;
        this.category = category;
        this.profileImage = profileImage;
        this.youtubeHandler = youtubeHandler;
        this.userName = userName;

    }
}
