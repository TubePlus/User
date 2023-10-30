package com.example.user_service.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, "C001", "해당 자원이 존재하지 않습니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "C002", "이미 존재하는 데이터입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Internal Server Error"),
    ENTITY_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "C005", "db 저장 실패"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C006", "잘못된 요청입니다."),
    INVALID_TYPE_VALUE(HttpStatus.NOT_FOUND, "C007", " Invalid Type Value"),

    /*로그인*/
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "L001", "로그인이 필요합니다."),
    NOT_FOUND_LOGIN_USER(HttpStatus.BAD_REQUEST, "L002", "해당 회원이 존재하지 않습니다."),

    /*유저*/
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 유저입니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "U002", "이미 존재하는 유저네임입니다."),
    NOT_CREATOR(HttpStatus.NOT_FOUND, "U003", "해당 유저는 크리에이터가 아닙니다."),
    INACTIVE_USER(HttpStatus.BAD_REQUEST, "U004", "휴면유저입니다."),
    BANNED_USER(HttpStatus.BAD_REQUEST, "U005", "정지된 유저입니다."),
    TEMPORAL_BANNED_USER(HttpStatus.BAD_REQUEST, "U006", "일시정지된 유저입니다."),
    DELETED_USER(HttpStatus.BAD_REQUEST, "U007", "탈퇴한 유저입니다."),

    /*유튜브*/
    YOUTUBE_ERROR(HttpStatus.BAD_REQUEST, "Y001", "유튜브 API 요청에 실패하였습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
