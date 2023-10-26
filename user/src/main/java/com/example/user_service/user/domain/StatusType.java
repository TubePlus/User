package com.example.user_service.user.domain;

public enum StatusType {

    DELETED, // 탈퇴계정
    ACTIVE, // 활동중인 정상 계정
    INACTIVE, // 휴면 계정
    BANNED, // 관리자에 의해 정지된 계정
    TEMPORAL_BAN // 일시정지된 계정
}
