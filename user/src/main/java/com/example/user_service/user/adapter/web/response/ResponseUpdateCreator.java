package com.example.user_service.user.adapter.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
/**
 * 일반 유저 -> 크리에이터 전환 시, 크리에이터의 카테고리 변경 시 사용
 */
public class ResponseUpdateCreator {

    private String username;
    private Boolean isCreator;
    private String categoryName;
}
