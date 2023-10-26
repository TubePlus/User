package com.example.user_service.user.adapter.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class RequestDeleteCreator {

    @NotNull
    private String uuid;
}
