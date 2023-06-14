package kr.anabada.anabadaserver.global.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {
    @Test
    @DisplayName("커스텀에러에서 에러코드가 잘 반환되는지 확인")
    public void 커스텀에러_에러코드_정상반환() {
        ErrorCode err = ErrorCode.ONLY_ACCESS_USER;
        CustomException e = new CustomException(ErrorCode.ONLY_ACCESS_USER);
        ResponseEntity<ErrorResponse> response = new GlobalExceptionHandler().handleCustomException(e);
        assertEquals(err.getStatus(), response.getStatusCode());
    }

}