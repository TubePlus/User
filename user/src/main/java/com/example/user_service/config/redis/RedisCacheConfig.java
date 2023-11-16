//package com.example.user_service.config.redis;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableCaching
//public class RedisCacheConfig {
//
//    private final RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public CacheManager redisCacheManager() {
//
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                // 키 String 직렬화
//                .serializeKeysWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(
//                                new StringRedisSerializer()))
//                // 값 String 직렬화
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(
//                                new GenericJackson2JsonRedisSerializer(objectMapper())))
//                .entryTtl(Duration.ofHours(25L)); // 캐시 만료 시간 25시간
//
//        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
//                redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
//
//        return redisCacheManager;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//
//        return new ObjectMapper()
//                // JDK ServiceLoader에서 기본적으로 제공하는 모듈
//                .findAndRegisterModules()
//                // JSON 형태로 저장하거나 출력 시 indentation 맞춰서 formatting 해줌
//                .enable(SerializationFeature.INDENT_OUTPUT)
//                // Date를 TimeStamp 형식으로 직렬화하지 못하게 하고
//                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                // 알 수 없는 속성이 있어도 무시하고 역직렬화 수행
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                // JavaTimeModule을 등록하여 LocalDateTime을 직렬화할 수 있게 함
//                .registerModules(new JavaTimeModule());
//    }
//}
