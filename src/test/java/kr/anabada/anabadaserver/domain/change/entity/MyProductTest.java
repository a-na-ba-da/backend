package kr.anabada.anabadaserver.domain.change.entity;

import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.domain.change.dto.MyProductResponse;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("MyProduct 엔티티 테스트")
@ExtendWith(SpringExtension.class)
class MyProductTest {

    @Test
    @DisplayName("MyProduct 엔티티를 MyProductResponse로 변환한다.")
    void toResponse() {
        // given
        User user = User.builder().id(1L).build();

        List<Image> images = List.of(
                Image.builder().id(UUID.randomUUID()).build(),
                Image.builder().id(UUID.randomUUID()).build()
        );

        MyProduct myProduct = MyProduct.builder()
                .id(1L)
                .owner(user)
                .name("name")
                .content("content")
                .originalPrice(1000)
                .images(images)
                .build();

        // when
        MyProductResponse myProductResponse = myProduct.toResponse();

        // then
        assertAll(
                () -> {
                    Assertions.assertEquals(myProduct.getId(), myProductResponse.getId());
                    Assertions.assertEquals(myProduct.getName(), myProductResponse.getName());
                    Assertions.assertEquals(myProduct.getContent(), myProductResponse.getContent());
                    Assertions.assertEquals(myProduct.getOriginalPrice(), myProductResponse.getOriginalPrice());
                    Assertions.assertEquals(myProduct.getImages().size(), myProductResponse.getImages().size());
                }
        );
    }

    @Test
    @DisplayName("상품 이미지가 NULL이여도 MyProduct 엔티티를 MyProductResponse로 변환한다.")
    void testToResponse() {
        // given
        User user = User.builder().id(1L).build();
        MyProduct myProduct = MyProduct.builder()
                .id(1L)
                .owner(user)
                .name("name")
                .content("content")
                .originalPrice(1000)
                .images(null)
                .build();

        // when
        MyProductResponse myProductResponse = myProduct.toResponse();

        // then
        assertAll(
                () -> {
                    Assertions.assertEquals(myProduct.getId(), myProductResponse.getId());
                    Assertions.assertEquals(myProduct.getName(), myProductResponse.getName());
                    Assertions.assertEquals(myProduct.getContent(), myProductResponse.getContent());
                    Assertions.assertEquals(myProduct.getOriginalPrice(), myProductResponse.getOriginalPrice());
                    Assertions.assertNull(myProductResponse.getImages());
                }
        );
    }
}