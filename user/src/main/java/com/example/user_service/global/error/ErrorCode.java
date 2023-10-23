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
    FAIL_LOGIN(HttpStatus.BAD_REQUEST, "L002", "로그인 실패"),
    NEED_INTERGRATED_LOGIN(HttpStatus.NOT_FOUND, "L003", "통합ID 로그인이 필요합니다"),

    /*유저*/
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 유저입니다."),
//    FIND_SELF(HttpStatus.BAD_REQUEST, "U002", "나에게는 선물할 수 없어요!"),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "U002", "이미 존재하는 유저네임입니다."),

    /*포인트*/
    INVALID_POINT_GIFT_AMOUNT(HttpStatus.BAD_REQUEST, "P001", "유효하지 않은 포인트 선물 입력"),
    INVALID_POINT_REPLY_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "P002", "유효하지 않은 포인트 선물 응답"),
    INVALID_POINT_PASSWORD(HttpStatus.UNAUTHORIZED,"P003","잘못된 포인트 패스워드"),

    /*store*/
    DUPLICATE_FAV_STORE(HttpStatus.CONFLICT, "T001", "이미 등록된 단골매장입니다"),

    /*sms message*/
    EXTERNAL_NCP_SERVER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "F001", "인증번호 발송에 실패하였습니다"),
    CERT_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "F002", "인증번호가 만료되었습니다"),
    CERT_CODE_INVALID(HttpStatus.BAD_REQUEST, "F002", "인증번호가 유효하지 않습니다");


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
