package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestLogInUser;
import com.example.user_service.user.application.ports.output.dto.LogInDto;
import lombok.Builder;
import lombok.Getter;

public interface LogInUseCase {

    LogInDto logInUser(LogInQuery logInQuery);

    // Request를 Dto로 변환
    @Getter
    @Builder
    class LogInQuery {

        private String email;

        public static LogInQuery toQuery(RequestLogInUser requestLoginUser) {
            return LogInQuery.builder()
                    .email(requestLoginUser.getEmail())
                    .build();
        }
    }
}
