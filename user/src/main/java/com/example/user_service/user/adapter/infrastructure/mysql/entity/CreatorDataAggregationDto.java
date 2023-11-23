package com.example.user_service.user.adapter.infrastructure.mysql.entity;

public class CreatorDataAggregationDto {
    private Long communityId;
    private String userUuid;
    private String category;
    private Long memberCount;
    private String profileImage;
    private String youtubeHandler;
    private String userName;
    private String communityName;
    private String youtubeName;

    public CreatorDataAggregationDto(String uuid, String category, String profileImage, String youtubeHandler, String userName) {
        this.userUuid = uuid;
        this.category = category;
        this.profileImage = profileImage;
        this.youtubeHandler = youtubeHandler;
        this.userName = userName;
    }
}
