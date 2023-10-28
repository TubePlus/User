package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestReadUserInfo;
import com.example.user_service.user.adapter.web.request.RequestSoftDeleteUser;
import com.example.user_service.user.adapter.web.request.RequestToggleDarkMode;
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

        public static GetUserInfoQuery toQuery(RequestReadUserInfo requestReadUserInfo) {

            return GetUserInfoQuery.builder()
                    .uuid(requestReadUserInfo.getUuid())
                    .build();
        }
    }

    @Getter
    @Builder
    class SoftDeleteUserQuery {

        private String uuid;

        public static SoftDeleteUserQuery toQuery(RequestSoftDeleteUser requestSoftDeleteUser) {

            return SoftDeleteUserQuery.builder()
                    .uuid(requestSoftDeleteUser.getUuid())
                    .build();
        }
    }

    @Getter
    @Builder
    class ToggleDarkModeQuery {

        private String uuid;

        public static ToggleDarkModeQuery toQuery(RequestToggleDarkMode requestToggleDarkMode) {

            return ToggleDarkModeQuery.builder()
                    .uuid(requestToggleDarkMode.getUuid())
                    .build();
        }
    }
}
