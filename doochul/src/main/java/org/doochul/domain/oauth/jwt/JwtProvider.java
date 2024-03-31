package org.doochul.domain.oauth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createToken(final Long id) {
        final Date now = new Date();
        final Claims claims = Jwts.claims().setSubject(String.valueOf(id));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (30 * 60 * 1000L)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
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
