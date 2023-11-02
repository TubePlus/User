package com.example.user_service.user.adapter.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ResponseCheckCreator {

    // todo: 서버간 통신 API 사용하지 않을 경우에 삭제해야합니다.
    private Boolean isCreator;
}
