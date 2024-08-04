package org.doochul.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.doochul.common.exception.BackEndApplicationException;
import org.doochul.common.exception.ErrorCodes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public static String extract(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorizationHeader)) {
            throw new BackEndApplicationException(ErrorCodes.TOKEN_NULL_EXCEPTION, HttpStatus.BAD_REQUEST);
        }

        validateAuthorizationFormat(authorizationHeader);
        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
    }

    private static void validateAuthorizationFormat(final String authorizationHeader) {
        if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            throw new BackEndApplicationException(ErrorCodes.TOKEN_BEARER_TYPE_EXCEPTION,HttpStatus.BAD_REQUEST);
        }
    }
}
