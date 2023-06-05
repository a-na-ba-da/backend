package kr.anabada.anabadaserver.global.exception;

public enum ErrorCode {

    // Common
    ONLY_ACCESS_USER(501, "C001", "유저만 사용 할 수 있는 기능입니다."),

    // R
    NOT_FOUND_RECYCLE_POST(404, "R001", "찾을수 없는 게시물입니다."),
    CANT_DUPLICATE_LIKE(500, "R002", "좋아요를 이미 누른 게시물입니다.");
    // TODO 상태코드 적절한지 확인 후 변경

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
