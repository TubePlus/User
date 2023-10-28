package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestChangeUsername;
import com.example.user_service.user.application.ports.output.dto.ChangeUsernameDto;
import com.example.user_service.user.application.ports.output.dto.IsDuplicateDto;
import lombok.Builder;
import lombok.Getter;

public interface UsernameUseCase {

    // username 중복 체크(기존의 username을 새로운 username으로 변경 시 사용)
    ChangeUsernameDto changeUsername(ChangeUsernameQuery changeUsernameQuery);
    IsDuplicateDto checkDuplicateName(CheckDuplicateUsernameQuery checkDuplicateUsernameQuery);

    // Request를 Dto로 변환
    @Getter
    @Builder
    class ChangeUsernameQuery {

        private String uuid;
        private String username;

        public static ChangeUsernameQuery toQuery(RequestChangeUsername requestChangeUsername) {
            return ChangeUsernameQuery.builder()
                    .username(requestChangeUsername.getUsername())
                    .uuid(requestChangeUsername.getUuid())
                    .build();
        }
    }

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