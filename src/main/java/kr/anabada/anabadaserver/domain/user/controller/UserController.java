package kr.anabada.anabadaserver.domain.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Secured("ROLE_USER")
    @GetMapping("/loginInfo")
    public String loginInfo(Authentication authentication) {
        log.info("auth : {}", authentication);

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        return oAuth2User.toString();
    }

    //    @Secured("ROLE_USER")
    @GetMapping("/test/user")
    public String testUser() {
        return "test user";
    }

}
