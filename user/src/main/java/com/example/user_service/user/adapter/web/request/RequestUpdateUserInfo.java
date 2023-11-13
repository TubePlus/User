package com.example.user_service.user.adapter.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateUserInfo {

    private String uuid;
    private String username;
    private String profileImage;
    private String locale;
    private String bio;
}
