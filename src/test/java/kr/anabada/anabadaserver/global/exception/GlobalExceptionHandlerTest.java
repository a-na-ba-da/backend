package kr.anabada.anabadaserver.global.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    @DisplayName("CustomException 에러 상태 코드를 잘 반환하는지 확인")
    void testHandleCustomException_Positive() {
        ErrorCode err = ErrorCode.ONLY_ACCESS_USER;
        CustomException e = new CustomException(err);
        ResponseEntity<ErrorResponse> response = new GlobalExceptionHandler().handleCustomException(e);
        assertEquals(err.getStatus(), response.getStatusCode());
    }

}