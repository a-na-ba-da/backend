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
<<<<<<< HEAD
    ONLY_ACCESS_USER(501, "C001", "유저만 사용 할 수 있는 기능입니다."),

    // R
    NOT_FOUND_RECYCLE_POST(404, "R001", "찾을수 없는 게시물입니다."),
    CANT_DUPLICATE_LIKE(500, "R002", "좋아요를 이미 누른 게시물입니다.");
    // TODO 상태코드 적절한지 확인 후 변경
=======
    ONLY_ACCESS_USER(NOT_IMPLEMENTED, "C001", "로그인 이후 사용 할 수 있는 기능입니다."),
    NOT_FOUND_BUY_TOGETHER(NOT_FOUND, "C002", "해당 게시글을 찾을 수 없습니다."),


    // Report
    CANT_REPORT(BAD_REQUEST, "R001", "신고 할 수 없습니다. 요청값을 확인해주세요."),
    DUPLICATE_REPORT(BAD_REQUEST, "R002", "이미 신고한 이력이 있습니다. 중복 신고는 불가능합니다.");
>>>>>>> a459d0e681cfdbe0ccc85ec00f7b224935efb4fa

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
