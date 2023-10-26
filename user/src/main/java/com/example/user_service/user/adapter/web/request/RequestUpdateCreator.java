package com.example.user_service.user.adapter.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
/**
 * 일반 유저 -> 크리에이터 전환 시, 크리에이터의 카테고리 변경 시 사용
 */
public class RequestUpdateCreator {

    @NotNull
    private String uuid;

    @NotNull
    private String categoryName;
}
