package kr.anabada.anabadaserver.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2Service oauthService;
    private final OAuth2SuccessHandler oauthSuccessHandler;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        // csrf 설정 해제
                        AbstractHttpConfigurer::disable
                )
                .httpBasic(
                        AbstractHttpConfigurer::disable
                )
                .headers(
                        AbstractHttpConfigurer::disable // h2
                )
                .authorizeHttpRequests(
                        it -> it
                                // api-docs, swagger-ui, h2
                                .requestMatchers("/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/test/user/**").hasRole("USER")
                                .requestMatchers("/test/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .anyRequest().permitAll()
                )
                .formLogin(
                        // 폼 로그인 해제
                        AbstractHttpConfigurer::disable
                )
                .oauth2Login(
                        it -> it
                                // success handler 설정
                                .successHandler(oauthSuccessHandler)
                                .userInfoEndpoint(
                                        userInfo -> userInfo.userService(oauthService)
                                )
                )
                .addFilterBefore(
                        // jwt token filter 설정
                        jwtTokenFilter,
                        OAuth2LoginAuthenticationFilter.class
                )
                .exceptionHandling(
                        it -> {
                            //authenticationEntryPoint
                            it.authenticationEntryPoint(
                                    (request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value())
                            );

                            //accessDeniedHandler
                            it.accessDeniedHandler(
                                    (request, response, accessDeniedException) -> response.sendError(HttpStatus.FORBIDDEN.value())
                            );
                        }
                );

        return http.build();
    }
}
