package kr.anabada.anabadaserver.global.exception;

public enum ErrorCode {

    // Common
    ONLY_ACCESS_USER(501, "C001", "유저만 사용 할 수 있는 기능입니다.");
    
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
