package com.example.user_service.user.application.service;

import com.example.user_service.user.application.service.response.GetMyChannelDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

public interface YoutubeService {
    GetMyChannelDto getMyChannelInfo(String token) throws JsonProcessingException;
    GetMyChannelDto getMyProfileImageAndHandler(String token) throws JsonProcessingException;
}
