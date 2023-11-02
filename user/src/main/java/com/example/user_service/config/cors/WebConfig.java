package com.example.user_service.config.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // todo: 외부 API 호출할 때도 WebClient가 필요하기 때문에 삭제될 수도 있음.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // CORS를 적용할 URL 패턴 정의
                .allowedOrigins("*") // 허용할 출처
                .allowedMethods("GET", "POST") // 허용할 HTTP method
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }
}