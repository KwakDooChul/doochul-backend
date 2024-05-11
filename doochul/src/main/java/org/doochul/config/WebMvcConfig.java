package org.doochul.config;

import lombok.RequiredArgsConstructor;
import org.doochul.common.interceptor.AuthInterceptor;
import org.doochul.common.resolver.CurrentUserArgumentResolver;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(jwtProvider))
                .addPathPatterns("/**")
                .excludePathPatterns("/login/kakao", "/oauth/kakao");
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.LOCATION)
                .maxAge(3600);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserArgumentResolver(jwtProvider));
    }
}
