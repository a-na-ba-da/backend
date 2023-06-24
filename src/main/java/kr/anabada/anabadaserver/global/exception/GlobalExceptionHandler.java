package kr.anabada.anabadaserver.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static kr.anabada.anabadaserver.global.exception.ErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * jakarta.validation.constraints.* 유효성 검사 실패 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponse response = new ErrorResponse(INVALID_INPUT_VALUE.getErrorCode(), INVALID_INPUT_VALUE.getMessage(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    /**
     * IllegalArgumentException 발생 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        final ErrorResponse response = new ErrorResponse(ILLEGAL_ARGUMENT_EXCEPTION.getErrorCode(), ILLEGAL_ARGUMENT_EXCEPTION.getMessage(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    /**
     * IllegalStateException 발생 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {IllegalStateException.class})
    protected ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        final ErrorResponse response = new ErrorResponse(ILLEGAL_STATE_EXCEPTION.getErrorCode(), ILLEGAL_STATE_EXCEPTION.getMessage(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    /**
     * CustomException 발생 시, Response 규격에 맞춰 에러 메시지를 반환한다.
     */
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
