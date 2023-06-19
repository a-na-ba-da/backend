package kr.anabada.anabadaserver.domain.change.respository;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.TestConfig;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.MyProductRepositoryImpl.SearchProductRecord;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class MyProductRepositoryTest {

    @Autowired
    private MyProductRepository myProductRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("유저의 물건 목록 조회")
    void findUserProductList() {
        User user = User.builder()
                .nickname("test")
                .email("test@test.com")
                .activated(true)
                .role("ROLE_USER")
                .build();
        em.persist(user);

        MyProduct myProduct = MyProduct.builder()
                .owner(user)
                .name("test")
                .content("test")
                .originalPrice(10000)
                .status(ProductStatus.AVAILABLE)
                .images(null)
                .build();
        em.persist(myProduct);

        MyProduct myProduct2 = MyProduct.builder()
                .owner(user)
                .name("교환 완료 물건")
                .content("교환 완료 물건")
                .originalPrice(10000)
                .status(ProductStatus.CHANGED)
                .images(null)
                .build();
        em.persist(myProduct2);

        // when
        var page = PageRequest.of(0, 10);
        var searchOption = new SearchProductRecord(user, null, page, false);
        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        // then
        Assertions.assertThat(myProductResponses.getTotalElements()).isEqualTo(1);
    }
}