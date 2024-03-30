package org.doochul.domain.oauth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 토큰 생성
    public String createToken(final String userPk) {
        final Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        final Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + (30 * 60 * 1000L))) // 토큰 유효시각 설정 (30분)
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 암호화 알고리즘과, secret 값
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public Long getPayload(final String token) {
        String sub = getClaims(token)
                .getBody()
                .get("sub", String.class);
        return Long.parseLong(sub);
    }

    public Jws<Claims> getClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    // 토큰 유효성, 만료일자 확인
    public boolean isValidToken(final String jwtToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
