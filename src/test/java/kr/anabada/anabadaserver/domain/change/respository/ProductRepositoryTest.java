package kr.anabada.anabadaserver.domain.change.respository;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.AnabadaServerApplication;
import kr.anabada.anabadaserver.TestConfig;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepositoryImpl.SearchProductRecord;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@ContextConfiguration(classes = AnabadaServerApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository myProductRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("검색할때 검색어를 입력하지 않으면 모든 물건들이 조회된다.")
    void findUserProductList() {
        // given
        User user = createUserA();
        em.persist(user);

        MyProduct myProduct = createMyProduct(user, "123키워드123", "물건 설명", ProductStatus.AVAILABLE);
        MyProduct myProduct2 = createMyProduct(user, "물건 이름", "물건 설명", ProductStatus.AVAILABLE);
        myProductRepository.saveAll(List.of(myProduct, myProduct2));

        var page = PageRequest.of(0, 10);
        var searchOption = new SearchProductRecord(user, null, page, false);
        // when
        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        // then
        assertThat(myProductResponses.getContent()).hasSize(2)
                .extracting("name", "content")
                .containsExactlyInAnyOrder(
                        tuple("123키워드123", "물건 설명"),
                        tuple("물건 이름", "물건 설명"));
    }

    @Test
    @DisplayName("AVAILABLE 상태가 아닌 물건들은 조회되지 않는다.")
    void findUserProductList1() {
        // given
        User user = createUserA();
        em.persist(user);

        MyProduct myProduct = createMyProduct(user, "test", "다른 상태코드 물건", ProductStatus.REQUESTING);
        MyProduct myProduct2 = createMyProduct(user, "교환 완료 물건", "교환 완료 물건", ProductStatus.CHANGED);
        myProductRepository.saveAll(List.of(myProduct, myProduct2));

        var page = PageRequest.of(0, 10);
        var searchOption = new SearchProductRecord(user, null, page, false);

        // when
        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        // then
        assertThat(myProductResponses.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("검색할때 검색어를 입력하면 물건 이름에 키워드가 포함된 물건들이 조회된다.")
    void findUserProductList2() {
        // given
        User user = createUserA();
        em.persist(user);

        MyProduct myProduct = createMyProduct(user, "123키워드123", "물건 설명", ProductStatus.AVAILABLE);
        MyProduct myProduct2 = createMyProduct(user, "물건 이름", "물건 설명", ProductStatus.AVAILABLE);
        myProductRepository.saveAll(List.of(myProduct, myProduct2));

        var keyword = "키워드";
        var page = PageRequest.of(0, 10);
        var searchOption = new SearchProductRecord(user, keyword, page, false);

        // when
        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        // then
        assertThat(myProductResponses.getContent()).hasSize(1)
                .extracting("name", "content")
                .contains(tuple("123키워드123", "물건 설명"));
    }

    @Test
    @DisplayName("검색할때 검색어를 입력하면 물건 설명에 키워드가 포함된 물건이 조회된다.")
    void findUserProductList3() {
        // given
        User user = createUserA();
        em.persist(user);

        MyProduct myProduct = createMyProduct(user, "제목", "123키워드123", ProductStatus.AVAILABLE);
        MyProduct myProduct2 = createMyProduct(user, "제목", "물건 설명", ProductStatus.AVAILABLE);
        myProductRepository.saveAll(List.of(myProduct, myProduct2));

        var keyword = "키워드";
        var page = PageRequest.of(0, 10);
        var searchOption = new SearchProductRecord(user, keyword, page, false);

        // when
        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        // then
        assertThat(myProductResponses.getContent()).hasSize(1)
                .extracting("name", "content")
                .contains(tuple("제목", "123키워드123"));
    }

    @Test
    @DisplayName("user 엔티티를 지정하지 않으면 모든 사용자의 물건을 조회한다.")
    void findAllProducts1() {
        // given
        User userA = createUserA();
        User userB = createUserB();
        em.persist(userA);
        em.persist(userB);

        MyProduct myProduct = createMyProduct(userA, "상품1", "설명1", ProductStatus.AVAILABLE);
        MyProduct myProduct2 = createMyProduct(userB, "(교환된)상품2", "(교환된)설명2", ProductStatus.CHANGED);
        MyProduct myProduct3 = createMyProduct(userB, "상품3", "설명3", ProductStatus.AVAILABLE);
        myProductRepository.saveAll(List.of(myProduct, myProduct2, myProduct3));

        var page = PageRequest.of(0, 10);
        var searchOption = new SearchProductRecord(null, "", page, false);

        // when
        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        // then
        assertThat(myProductResponses.getContent()).hasSize(2)
                .extracting("name", "content")
                .containsExactlyInAnyOrder(
                        tuple("상품1", "설명1"),
                        tuple("상품3", "설명3"));
    }

    @Test
    @DisplayName("모든 사용자의 물건을 조회할때 검색어를 입력하면 물건 이름에 키워드가 포함된 물건들이 조회된다.")
    void findAllProducts2() {
        // given
        User userA = createUserA();
        User userB = createUserB();
        em.persist(userA);
        em.persist(userB);

        MyProduct myProduct = createMyProduct(userA, "물건1 키워드키워드", "설명1", ProductStatus.AVAILABLE);
        MyProduct myProduct2 = createMyProduct(userB, "물건2", "설명2", ProductStatus.AVAILABLE);
        MyProduct myProduct3 = createMyProduct(userB, "물건3 키워드키워드", "설명3", ProductStatus.AVAILABLE);
        myProductRepository.saveAll(List.of(myProduct, myProduct2, myProduct3));

        var page = PageRequest.of(0, 10);
        var keyword = "키워드";
        var searchOption = new SearchProductRecord(null, keyword, page, false);

        // when
        var myProductResponses = myProductRepository.findUserProductList(searchOption);

        // then
        assertThat(myProductResponses.getContent()).hasSize(2)
                .extracting("name", "content")
                .contains(tuple("물건1 키워드키워드", "설명1"), tuple("물건3 키워드키워드", "설명3"));
    }

    private User createUserA() {
        return User.builder()
                .nickname("userA")
                .email("userA@test.com")
                .activated(true)
                .role("ROLE_USER")
                .build();
    }

    private User createUserB() {
        return User.builder()
                .nickname("userB")
                .email("userB@test.com")
                .activated(true)
                .role("ROLE_USER")
                .build();
    }

    private MyProduct createMyProduct(User owner, String name, String content, ProductStatus status) {
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