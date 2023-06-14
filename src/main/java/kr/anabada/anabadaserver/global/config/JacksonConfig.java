package kr.anabada.anabadaserver.global.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        // 빈 객체에서 직렬화 오류 해결
        builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        // json에서 LocalDateTime이 Array형식으로 나오는 문제 해결
        builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return builder;
    }
}

