package com.example.user_service.user.application.service;


import com.example.user_service.user.application.service.response.GetMyChannelDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
@ToString
@RequiredArgsConstructor
public class YoutubeServiceImpl implements YoutubeService{

    private final WebClient webClient;

    // 내 채널 정보 조회 기능
    @Override
    public GetMyChannelDto getMyChannelInfo(String token) throws JsonProcessingException {

        String partValues =
                "statistics,brandingSettings,snippet";
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/channels")
                        .queryParam(
                                "part", partValues)
                        .queryParam("mine", true)
                        .build()
                )
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        String profileImageUrl =
                jsonNode.get("items").get(0).get("snippet").get("thumbnails").get("high").get("url").asText();
        String username = jsonNode.get("items").get(0).get("snippet").get("title").asText();
        String description = jsonNode.get("items").get(0).get("snippet").get("description").asText();
        String viewCount = jsonNode.get("items").get(0).get("statistics").get("viewCount").asText();
        String subscriberCount = jsonNode.get("items").get(0).get("statistics").get("subscriberCount").asText();
        String videoCount = jsonNode.get("items").get(0).get("statistics").get("videoCount").asText();
        String bannerImageUrl =
                jsonNode.get("items").get(0).get("brandingSettings").get("image").get("bannerExternalUrl").asText();

        return GetMyChannelDto.builder()
                .url(profileImageUrl)
                .title(username)
                .description(description)
                .viewCount(viewCount)
                .subscriberCount(subscriberCount)
                .videoCount(videoCount)
                .bannerExternalUrl(bannerImageUrl)
                .build();
    }

    // todo: JSONParser와 비교해서 시간 측정
    // 회원가입 및 로그인 시 유튜브 프로필 사진 업데이트
    @Override
    public GetMyChannelDto getMyProfileImage(String token) throws JsonProcessingException {

        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/channels")
                        .queryParam(
                                "part", "snippet")
                        .queryParam("mine", true)
                        .build()
                )
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        String profileImageUrl =
                jsonNode.get("items").get(0).get("snippet").get("thumbnails").get("high").get("url").asText();

        return GetMyChannelDto.builder()
                .url(profileImageUrl)
                .build();
    }
}