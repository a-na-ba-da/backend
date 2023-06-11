package kr.anabada.anabadaserver.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorCode {

    // INVALID INPUT VALUE - 에러코드 C000
    INVALID_INPUT_VALUE(BAD_REQUEST, "C000", "입력 값이 올바르지 않습니다."),
    NOT_EXIST_BUY_PLACE(NOT_FOUND, "C000", "구매 장소를 입력해주세요."),
    INVALID_BUY_DATE(BAD_REQUEST, "C000", "구매 날짜가 올바르지 않습니다."),
    NOT_EXIST_IMAGE(UNPROCESSABLE_ENTITY, "C000", "이미지를 입력해주세요."),
    ILLEGAL_ARGUMENT_EXCEPTION(FORBIDDEN, "C000", "인수 값이 잘못되었습니다."),


    // Common
    ONLY_ACCESS_USER(NOT_IMPLEMENTED, "C001", "로그인 이후 사용 할 수 있는 기능입니다."),
    NOT_FOUND_BUY_TOGETHER(NOT_FOUND, "C002", "해당 게시글을 찾을 수 없습니다.");

    private final String errorCodeStr;
    private final String message;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String errorCodeStr, final String message) {
        this.status = status;
        this.message = message;
        this.errorCodeStr = errorCodeStr;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCodeStr;
    }
}
