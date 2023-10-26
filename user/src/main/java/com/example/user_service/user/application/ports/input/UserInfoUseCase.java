package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.application.ports.output.dto.SoftDeleteUserDto;
import com.example.user_service.user.application.ports.output.dto.ToggleDarkModeDto;
import com.example.user_service.user.application.ports.output.dto.ReadUserInfoDto;
import lombok.Builder;
import lombok.Getter;

public interface UserInfoUseCase {

    ReadUserInfoDto getUserInfo(GetUserInfoQuery getUserInfoQuery);
    SoftDeleteUserDto softDeleteUser(SoftDeleteUserQuery softDeleteUserQuery);
    ToggleDarkModeDto toggleDarkMode(ToggleDarkModeQuery toggleDarkModeQuery);

    // Request를 Dto로 변환
    @Getter
    @Builder
    class GetUserInfoQuery {

        private String uuid;

        public static GetUserInfoQuery toQuery(String uuid) {

            return GetUserInfoQuery.builder()
                    .uuid(uuid)
                    .build();
        }
    }

    @Getter
    @Builder
    class SoftDeleteUserQuery {

        private String uuid;

        public static SoftDeleteUserQuery toQuery(String uuid) {

            return SoftDeleteUserQuery.builder()
                    .uuid(uuid)
                    .build();
        }
    }

    @Getter
    @Builder
    class ToggleDarkModeQuery {

        private String uuid;

        public static ToggleDarkModeQuery toQuery(String uuid) {

            return ToggleDarkModeQuery.builder()
                    .uuid(uuid)
                    .build();
        }
    }
}
