package kr.anabada.anabadaserver.domain.change.repository;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.TestConfig;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepository;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepositoryImpl.SearchProductRecord;
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
    private ProductRepository myProductRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("유저의 물건 목록 조회 성공 1 | 기본 조회 상황")
    void findUserProductList() {
        User user = createUser();
        em.persist(user);

        MyProduct myProduct = createMyProduct(user, "test", "test", 10000, ProductStatus.AVAILABLE);
        em.persist(myProduct);

        var page = PageRequest.of(0, 10);
        var searchOption = new SearchProductRecord(user, null, page, false);

        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        Assertions.assertThat(myProductResponses.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(myProductResponses.getContent().get(0).getName()).isEqualTo(myProduct.getName());
    }

    @Test
    @DisplayName("유저의 물건 목록 조회 성공 2 | 교환완료 등의 상태가 다른 상품은 조회되지 않음")
    void findUserProductList2() {
        User user = createUser();
        em.persist(user);

        MyProduct myProduct = createMyProduct(user, "test", "다른 상태코드 물건", 10000, ProductStatus.REQUESTING);
        em.persist(myProduct);

        MyProduct myProduct2 = createMyProduct(user, "교환 완료 물건", "교환 완료 물건", 10000, ProductStatus.CHANGED);
        em.persist(myProduct2);

        var page = PageRequest.of(0, 10);
        var searchOption = new SearchProductRecord(user, null, page, false);

        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        Assertions.assertThat(myProductResponses.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("유저의 물건 목록 조회 성공 3 | 키워드 검색 정상 동작")
    void findUserProductList3() {
        User user = createUser();
        em.persist(user);

        MyProduct myProduct = createMyProduct(user, "123키워드123", "123키워드123", 10000, ProductStatus.AVAILABLE);
        em.persist(myProduct);

        MyProduct myProduct2 = createMyProduct(user, "제목", "내용", 10000, ProductStatus.AVAILABLE);
        em.persist(myProduct2);

        var page = PageRequest.of(0, 10);
        var keyword = "키워드";
        var searchOption = new SearchProductRecord(user, keyword, page, false);

        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        var searched = myProductResponses.getContent().get(0);

        Assertions.assertThat(myProductResponses.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(searched.getName()).isEqualTo(myProduct.getName());
    }

    private User createUser() {
        return User.builder()
                .nickname("test")
                .email("test@test.com")
                .activated(true)
                .role("ROLE_USER")
                .build();
    }

    private MyProduct createMyProduct(User owner, String name, String content, int originalPrice, ProductStatus status) {
        return MyProduct.builder()
                .owner(owner)
                .name(name)
                .content(content)
                .originalPrice(originalPrice)
                .status(status)
                .images(null)
                .build();
    }
}