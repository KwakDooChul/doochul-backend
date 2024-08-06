package org.doochul.common.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.doochul.common.exception.BackEndApplicationException;
import org.doochul.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationExtractor {

    private static final String AUTH_COOKIE_NAME = "Authorization";

    public static String extract(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            throw new BackEndApplicationException(ErrorCodes.TOKEN_NULL_EXCEPTION, HttpStatus.BAD_REQUEST);
        }

        final Cookie authCookie = getAuthorizationCookie(cookies);
        if (Objects.isNull(authCookie)) {
            throw new BackEndApplicationException(ErrorCodes.TOKEN_NULL_EXCEPTION, HttpStatus.BAD_REQUEST);
        }

        return authCookie.getValue();
    }

    private static Cookie getAuthorizationCookie(final Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTH_COOKIE_NAME)) {
                return cookie;
            }
        }
        return null;
    }
}
