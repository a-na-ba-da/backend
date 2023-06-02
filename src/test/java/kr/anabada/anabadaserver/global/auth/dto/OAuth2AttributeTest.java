package kr.anabada.anabadaserver.global.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OAuth2AttributeTest {

    @Test()
    @DisplayName("OAuth2Attribute.of() 에서 프로바이더가 없으면 NullPointerException 이 발생한다.")
    void OAuth2Attribute_of_NPE() {
        // check that NullPointerException is thrown
        assertThrows(NullPointerException.class,
                () -> OAuth2Attribute.of(null, null));
    }

}