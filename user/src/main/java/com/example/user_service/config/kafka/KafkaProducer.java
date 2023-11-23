package com.example.user_service.config.kafka;

import com.example.user_service.user.adapter.infrastructure.mysql.entity.CreatorDataAggregationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.topic1.name}")
    private String topic1; // topic1 = "creatorDataAggregation"

    public void sendMessage(String kafkaTopic, String message){
        System.out.println("kafka message send in 1:" + message);
        try{
            kafkaTemplate.send(kafkaTopic, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void producerCreateCreator(String uuid, String category, String profileImage, String youtubeHandler, String userName)
            throws JsonProcessingException {
        CreatorDataAggregationDto creatorDataAggregationDto
                = new CreatorDataAggregationDto(uuid, category, profileImage, youtubeHandler, userName);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = objectMapper.writeValueAsString(creatorDataAggregationDto);
        sendMessage(topic1,jsonInString);
    }
}
