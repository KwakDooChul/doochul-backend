package org.doochul.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtTokenProvider;
    List<String> list = List.of(
            "/login/kakao",
            "/oauth/kakao"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws IOException, ServletException {
        log.info("인증 체크 필터 시작");

        final String token = jwtTokenProvider.resolveToken(request);
        if (list.contains(request.getRequestURI())) {
            log.info("인증 체크 필터 넘어가기");
            chain.doFilter(request,response);
            return;
        }

        if (token != null && jwtTokenProvider.isValidToken(token)) {
            log.info("토큰이 유효합니다.");
        } else {
            return;
        }

        chain.doFilter(request, response);
    }
}
