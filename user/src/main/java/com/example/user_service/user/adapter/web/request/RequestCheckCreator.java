package com.example.user_service.user.adapter.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCheckCreator {

    // todo: 서버간 통신 API 사용하지 않을 경우에 삭제해야합니다.
    private String uuid;
}
