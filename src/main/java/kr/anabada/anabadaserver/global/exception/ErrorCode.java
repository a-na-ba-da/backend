package kr.anabada.anabadaserver.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Common

    // TODO HTTPSTATUS로 변경
    ONLY_ACCESS_USER(501, "C001", "유저만 사용 할 수 있는 기능입니다."),
    NOT_FOUND_BUY_TOGETHER(HttpStatus.NOT_FOUND.value(), "C002", "해당 게시글을 찾을 수 없습니다.");

    private final String errorCodeStr;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String errorCodeStr, final String message) {
        this.status = status;
        this.message = message;
        this.errorCodeStr = errorCodeStr;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCodeStr;
    }
}
