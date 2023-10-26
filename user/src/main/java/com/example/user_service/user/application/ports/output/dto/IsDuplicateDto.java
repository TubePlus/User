package com.example.user_service.user.application.ports.output.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class IsDuplicateDto {

    private Boolean isDuplicate;
}
