package com.example.user_service.user.application.ports.output.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DuplicateCheckDto {

    private Boolean isDuplicate;

    public static DuplicateCheckDto formDuplicateCheckDto(Boolean isDuplicate) {
        return new DuplicateCheckDto(isDuplicate);
    }
}
