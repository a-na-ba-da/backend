package kr.anabada.anabadaserver.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorCode {

    // INVALID INPUT VALUE - 에러코드 C000
    INVALID_INPUT_VALUE(BAD_REQUEST, "C000", "입력 값이 올바르지 않습니다."),
    NOT_EXIST_BUY_PLACE(NOT_FOUND, "C000", "구매 장소를 입력해주세요."),
    NOT_EXIST_DELIVERY_PLACE(NOT_FOUND, "C000", "물건을 전달할 인근 장소를 입력해주세요."),
    INVALID_BUY_DATE(BAD_REQUEST, "C000", "구매 예정일이 올바르지 않습니다."),
    NOT_EXIST_IMAGE(UNPROCESSABLE_ENTITY, "C000", "이미지를 제대로 입력해주세요."),
    ILLEGAL_ARGUMENT_EXCEPTION(FORBIDDEN, "C000", "인수 값이 잘못되었습니다."),
    ILLEGAL_STATE_EXCEPTION(FORBIDDEN, "C000", "잘못된 메서드 호출입니다."),
    SEARCH_WORD_LENGTH(BAD_REQUEST, "C00", "검색어는 2글자 이상 입력해주세요."),


    // Common
    ONLY_ACCESS_USER(NOT_IMPLEMENTED, "C001", "로그인 이후 사용 할 수 있는 기능입니다."),
    NOT_FOUND_BUY_TOGETHER(NOT_FOUND, "C002", "해당 게시글을 찾을 수 없습니다."),


    // Report
    CANT_REPORT(BAD_REQUEST, "R001", "신고 할 수 없습니다. 요청값을 확인해주세요."),
    DUPLICATE_REPORT(BAD_REQUEST, "R002", "이미 신고한 이력이 있습니다. 중복 신고는 불가능합니다."),


    // Third-Party
    NAVER_PRODUCT_API_FAILED(SERVICE_UNAVAILABLE, "T001", "네이버 상품 API 호출에 실패했습니다. 잠시 후 다시 시도해주세요.");

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
