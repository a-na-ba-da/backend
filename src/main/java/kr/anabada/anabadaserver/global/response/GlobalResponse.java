package kr.anabada.anabadaserver.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class GlobalResponse<T> {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String errorCode;
    private final String message;
    private final T detail;
    private final ResponseEnum result;

    public GlobalResponse(String errorCode, String message, T detail, ResponseEnum result) {
        this.errorCode = errorCode;
        this.message = message;
        this.detail = detail;
        this.result = result;
    }

    public GlobalResponse(T detail) {
        this(null, "성공적으로 요청을 처리하였습니다.", detail, ResponseEnum.SUCCESS);
    }

    public static <T> ResponseEntity<GlobalResponse<T>> responseError(CustomException customException) {
        ErrorCode err = customException.getErrorCode();
        return ResponseEntity
                .status(err.getStatus())
                .body(new GlobalResponse<>(
                        err.getErrorCode(),
                        err.getMessage(),
                        null,
                        ResponseEnum.FAILURE));
    }

    public static <T> ResponseEntity<GlobalResponse<T>> responseError(ErrorCode errorCode, Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponse<>(
                        errorCode.getErrorCode(),
                        e.getLocalizedMessage(),
                        null,
                        ResponseEnum.FAILURE));
    }

}