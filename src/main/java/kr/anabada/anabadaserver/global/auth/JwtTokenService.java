package kr.anabada.anabadaserver.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import kr.anabada.anabadaserver.global.auth.dto.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class JwtTokenService {
    private static final String EMAIL = "email";
    private final String secret;
    private final CustomUserDetailsService userDetailsService;
    private Key key;

    public JwtTokenService(
            CustomUserDetailsService userDetailsService,
            @Value("${jwt.secret}") String secret) {
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(String email) {
        Claims claims = Jwts.claims().setSubject(EMAIL);
        claims.put(EMAIL, email);

        Date now = new Date();
        long tokenPeriod = 1000L * 60L * 60L * 24L * 30L * 3L; // 10분
        Date accessTokenExpiration = new Date(now.getTime() + tokenPeriod);
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L; // 3개월
        Date refreshTokenExpiration = new Date(now.getTime() + refreshPeriod);

        return JwtToken.builder()
                .accessToken(getJwt(claims, now, accessTokenExpiration))
                .accessTokenExpires(accessTokenExpiration)
                .refreshToken(getJwt(null, now, refreshTokenExpiration))
                .refreshTokenExpires(refreshTokenExpiration)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getJwt(Claims claims, Date now, Date tokenExpire) {
        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(tokenExpire)
                .signWith(key)
                .compact();
    }

    public String resolveToken(HttpServletRequest request, String tokenHeader) {
        String bearerToken = request.getHeader(tokenHeader);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        log.info("input Token : {}", token);
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String email = claims.get(EMAIL, String.class);
        if (email == null) {
            throw new IllegalArgumentException("Can't login with refresh token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
