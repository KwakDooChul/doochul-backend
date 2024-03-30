package org.doochul.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticationPrincipal.class) != null
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        String token = jwtProvider.resolveToken(httpServletRequest);
        System.out.println(token);

        return jwtProvider.getPayload(token);
    }
}
