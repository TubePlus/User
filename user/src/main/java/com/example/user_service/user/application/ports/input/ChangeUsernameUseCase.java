package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.application.ports.output.dto.ChangeUsernameDto;
import lombok.Builder;
import lombok.Getter;

public interface ChangeUsernameUseCase {

    // username 중복 체크(기존의 username을 새로운 username으로 변경 시 사용)
    ChangeUsernameDto changeUsername(ChangeUsernameQuery changeUsernameQuery);

    // Request를 Dto로 변환
    @Getter
    @Builder
    class ChangeUsernameQuery {

        private String uuid;
        private String username;

        public static ChangeUsernameQuery toQuery(String username, String uuid) {
            return ChangeUsernameQuery.builder()
                    .username(username)
                    .uuid(uuid)
                    .build();
        }
    }
}
