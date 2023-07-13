package kr.anabada.anabadaserver.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableCaching
@Configuration
@EnableScheduling
public class CacheConfig {

    /**
     * 사용자의 캐시를 10분마다 삭제한다.
     * 이 스케줄러는 사용자의 ban 여부 등을 10분마다 갱신하기 위해 사용된다.
     */
    @Scheduled(fixedDelay = 600000) // every 10 minutes
    @CacheEvict(value = "user", allEntries = true)
    public void userCacheEvict() {
        log.info("user cache evict");

    }
}
