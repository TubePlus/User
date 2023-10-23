package com.example.user_service.user.adapter.web.request;

import com.example.user_service.user.domain.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class RequestSignUpUser { //todo: locale 컬럼 추가될지도(확실하지 않음)

    @NotNull
    private String username; // username은 유튜브 api로 받아오는 것이 아닌 회원가입 단계에서 직접 입력받아야 함.
    private String profileImage;
    @NotNull
    private String email;
    @NotNull
    private String uuid;
}
