package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestSignUpUser;
import com.example.user_service.user.application.ports.output.dto.SignUpDto;
import com.example.user_service.user.domain.LangType;
import com.example.user_service.user.domain.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public interface SignUpUseCase {

    SignUpDto signUpUser(SignUpQuery signUpQuery);

    // Request를 Dto로 변환
    @Getter
    @Builder
    @ToString
    class SignUpQuery {

        private String username;
        private String profileImage;
        private RoleType role; // default
        private Boolean isCreator; // default
        private String email;
        private String uuid;
        private LangType language; // default
        private Boolean darkMode; // default
        private Integer softDelete; // default

        public static SignUpQuery toQuery(RequestSignUpUser requestSignUpUser) {
            return SignUpQuery.builder()
                    .username(requestSignUpUser.getUsername())
                    .profileImage(requestSignUpUser.getProfileImage())
                    .role(RoleType.MEMBER) // default
                    .isCreator(false) // default
                    .email(requestSignUpUser.getEmail())
                    .uuid(requestSignUpUser.getUuid())
                    .language(LangType.KOREAN) // default
                    .darkMode(false) // default
                    .softDelete(1) // default
                    .build();
        }
    }
}
