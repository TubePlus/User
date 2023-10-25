package com.example.user_service.user.application.service.response;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetMyChannelDto {

    private String url; // 프로필 이미지
    private String title; // 채널 이름
    private String description; // 채널 설명
    private String viewCount; // 총 조회수
    private String subscriberCount; // 구독자 수
    private String videoCount; // 총 비디오 수
    private String bannerExternalUrl; // 채널 배너 이미지
}
