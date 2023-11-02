package com.example.user_service.user.application.ports.output.dto;

import com.example.user_service.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CheckCreatorDto {

    // todo: 서버간 통신 API 사용하지 않을 경우에 삭제해야합니다.
    private Boolean isCreator;

    public static CheckCreatorDto formCheckCreatorDto(Boolean isCreator) {

        return CheckCreatorDto.builder()
                .isCreator(isCreator)
                .build();
    }
}
