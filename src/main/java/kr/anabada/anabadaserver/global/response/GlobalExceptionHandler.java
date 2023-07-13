package kr.anabada.anabadaserver.global.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static kr.anabada.anabadaserver.global.response.ErrorCode.*;
import static kr.anabada.anabadaserver.global.response.GlobalResponse.responseError;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * jakarta.validation.constraints.* 유효성 검사 실패 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<GlobalResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return responseError(INVALID_INPUT_VALUE, e);
    }

    /**
     * IllegalArgumentException 발생 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<GlobalResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return responseError(ILLEGAL_ARGUMENT_EXCEPTION, e);
    }

    /**
     * IllegalStateException 발생 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {IllegalStateException.class})
    protected ResponseEntity<GlobalResponse<Void>> handleIllegalStateException(IllegalStateException e) {
        return responseError(ILLEGAL_STATE_EXCEPTION, e);
    }

    /**
     * CustomException 발생 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<GlobalResponse<Void>> handleCustomException(CustomException e) {
        return responseError(e);
    }

    /**
     * HttpMessageNotReadableException 발생 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {org.springframework.http.converter.HttpMessageNotReadableException.class})
    protected ResponseEntity<GlobalResponse<Void>> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException e) {
        return responseError(INVALID_INPUT_VALUE, e);
    }
}
