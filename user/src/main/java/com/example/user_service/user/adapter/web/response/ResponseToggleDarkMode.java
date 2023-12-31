package com.example.user_service.user.adapter.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class ResponseToggleDarkMode {

    private Boolean darkMode;
}
