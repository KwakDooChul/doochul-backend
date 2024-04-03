package org.doochul.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        final String token = AuthorizationExtractor.extract(request);
        if (!jwtProvider.isValidToken(token)) {
            //TODO: 커스텀 exception으로 변경
            throw new IllegalArgumentException();
        }
        return true;
    }
}
