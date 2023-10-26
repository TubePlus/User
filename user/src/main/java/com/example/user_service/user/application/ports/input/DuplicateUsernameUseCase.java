package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.application.ports.output.dto.IsDuplicateDto;
import lombok.Builder;
import lombok.Getter;

public interface DuplicateUsernameUseCase {

    IsDuplicateDto checkDuplicateName(CheckDuplicateUsernameQuery checkDuplicateUsernameQuery);

    @Getter
    @Builder
    class CheckDuplicateUsernameQuery {

        private String username;

        public static CheckDuplicateUsernameQuery toQuery(String username) {

            return CheckDuplicateUsernameQuery.builder()
                    .username(username)
                    .build();
        }
    }
}
