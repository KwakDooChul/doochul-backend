package org.doochul.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.slf4j.MDC;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        MDC.put("request_id", uuid.toString());

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            MDC.put("controller", controllerName);
            MDC.put("method", methodName);
        }

        final String token = AuthorizationExtractor.extract(request);
        jwtProvider.getPayload(token);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
