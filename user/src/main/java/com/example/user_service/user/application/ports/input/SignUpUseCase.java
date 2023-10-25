package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestSignUpUser;
import com.example.user_service.user.application.ports.output.dto.SignUpDto;
import com.example.user_service.user.domain.RoleType;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public interface SignUpUseCase {

    SignUpDto signUpUser(SignUpQuery signUpQuery) throws JsonProcessingException;

    // Request를 Dto로 변환
    @Getter
    @Builder
    @ToString
    class SignUpQuery {

        private String username;
        private RoleType role; // default
        private Boolean isCreator; // default
        private String email;
        private String locale;
        private Boolean darkMode; // default
        private Integer softDelete; // default
        private String token;

        public static SignUpQuery toQuery(RequestSignUpUser requestSignUpUser) {
            return SignUpQuery.builder()
                    .username(requestSignUpUser.getUsername())
                    .role(RoleType.MEMBER) // default
                    .isCreator(false) // default
                    .email(requestSignUpUser.getEmail())
                    .locale(requestSignUpUser.getLocale())
                    .darkMode(false) // default
                    .softDelete(1) // default
                    .token(requestSignUpUser.getToken())
                    .build();
        }
    }
}
