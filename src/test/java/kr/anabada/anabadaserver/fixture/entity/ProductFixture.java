package kr.anabada.anabadaserver.fixture.entity;

import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.user.entity.User;

public class ProductFixture {
    public static MyProduct createProduct(User owner, String name, ProductStatus status) {
        return MyProduct.builder()
                .owner(owner)
                .name(name)
                .content("%s 입니다.".formatted(name))
                .originalPrice(10000)
                .images(null)
                .status(status)
                .build();
    }

    public static MyProduct createMyProduct(User owner, String name, String content, ProductStatus status) {
        return MyProduct.builder()
                .owner(owner)
                .name(name)
                .content(content)
                .originalPrice(10000)
                .status(status)
                .images(null)
                .build();
    }
}
