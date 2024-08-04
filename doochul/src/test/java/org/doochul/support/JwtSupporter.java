package org.doochul.support;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.oauth.token.Jwt;

public class JwtSupporter {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final JwtProvider jwtTokenProvider = new JwtProvider(secretKey);

    public static Jwt generateToken(final Long id) {
        return jwtTokenProvider.createToken(id);
    }
}
