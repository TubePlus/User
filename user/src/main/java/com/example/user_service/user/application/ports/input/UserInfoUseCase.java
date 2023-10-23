package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.application.ports.output.dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;

public interface UserInfoUseCase {

    UserInfoDto getUserInfo(UserInfoQuery userInfoQuery);

    // Request를 Dto로 변환
    @Getter
    @Builder
    class UserInfoQuery {

        private String uuid;

        public static UserInfoQuery toQuery(String uuid) {
            return UserInfoQuery.builder()
                    .uuid(uuid)
                    .build();
        }
    }
}
