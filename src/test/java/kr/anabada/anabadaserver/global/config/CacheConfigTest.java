package kr.anabada.anabadaserver.global.config;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import static kr.anabada.anabadaserver.fixture.entity.UserFixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("캐시 설정 테스트")
@Transactional(readOnly = true)
public class CacheConfigTest {

    @Autowired
    private CacheConfig cacheConfig;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EntityManager em;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("customUserDetailsService.loadUserByUsername() 호출 시 캐시가 생성된다.")
    void test() {
        // given
        User user = createUser("test@naver.com", "test");
        em.persist(user);

        // when
        customUserDetailsService.loadUserByUsername("test@naver.com");

        // then
        assertThat(cacheManager.getCache("user").get("test@naver.com")).isNotNull();
    }

    @Test
    @DisplayName("cacheConfig.userCacheEvict() 호출 시 user 캐시가 삭제된다.")
    void testUserCacheEvict_success() {
        // given
        User user = createUser("test@naver.com", "test");
        em.persist(user);
        customUserDetailsService.loadUserByUsername("test@naver.com");

        // when
        cacheConfig.userCacheEvict();

        // then
        assertThat(cacheManager.getCache("user").get("test@naver.com")).isNull();
    }
}