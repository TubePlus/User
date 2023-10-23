package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.application.ports.output.dto.DuplicateCheckDto;
import lombok.Builder;
import lombok.Getter;

public interface DuplicateCheckUseCase {

    DuplicateCheckDto checkDuplicateUsername(CheckUsernameQuery checkUsernameQuery);
    DuplicateCheckDto checkDuplicateGoogleAuth(CheckGoogleAuthQuery checkGoogleAuthQuery);

    // Request를 Dto로 변환
    @Getter
    @Builder
    class CheckUsernameQuery {

        private String username;

        public static CheckUsernameQuery toQuery(String username) {
            return CheckUsernameQuery.builder()
                    .username(username)
                    .build();
        }
    }

    @Getter
    @Builder
    class CheckGoogleAuthQuery {

        private String googleAuth;

        public static CheckGoogleAuthQuery toQuery(String googleAuth) {
            return CheckGoogleAuthQuery.builder()
                    .googleAuth(googleAuth)
                    .build();
        }
    }
}
