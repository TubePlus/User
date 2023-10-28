package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestLogInUser;
import com.example.user_service.user.adapter.web.request.RequestSignUpUser;
import com.example.user_service.user.adapter.web.request.RequestUserComeBack;
import com.example.user_service.user.application.ports.output.dto.ComeBackDto;
import com.example.user_service.user.application.ports.output.dto.LogInDto;
import com.example.user_service.user.application.ports.output.dto.SignUpDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import lombok.Getter;

public interface LogInUseCase {

    LogInDto logInUser(LogInQuery logInQuery) throws JsonProcessingException;
    SignUpDto signUpUser(SignUpQuery signUpQuery) throws JsonProcessingException;
    ComeBackDto comeBackUser(ComeBackQuery comeBackQuery) throws JsonProcessingException;


    // Request를 Dto로 변환
    @Getter
    @Builder
    class LogInQuery {

        private String email;
        private String token;

        public static LogInQuery toQuery(RequestLogInUser requestLoginUser) {

            return LogInQuery.builder()
                    .email(requestLoginUser.getEmail())
                    .token(requestLoginUser.getToken())
                    .build();
        }
    }

    @Getter
    @Builder
    class SignUpQuery {

        private String username;
        private String email;
        private String locale;
        private String token;

        public static SignUpQuery toQuery(RequestSignUpUser requestSignUpUser) {

            return SignUpQuery.builder()
                    .username(requestSignUpUser.getUsername())
                    .email(requestSignUpUser.getEmail())
                    .locale(requestSignUpUser.getLocale())
                    .token(requestSignUpUser.getToken())
                    .build();
        }
    }

    @Getter
    @Builder
    class ComeBackQuery {

        private String email;
        private String token;

        public static ComeBackQuery toQuery(RequestUserComeBack requestUserComeBack) {

            return ComeBackQuery.builder()
                    .email(requestUserComeBack.getEmail())
                    .token(requestUserComeBack.getToken())
                    .build();
        }
    }

}
