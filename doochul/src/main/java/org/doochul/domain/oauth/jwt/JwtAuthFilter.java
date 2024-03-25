package org.doochul.domain.oauth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.user.User;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("Security Filter Start");

//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        if (req.getMethod().equals(HttpMethod.POST.name())) {
//            log.info("POST 요청");
//            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//            if (token.equals("token")) {
//                log.info("Filter 4");
//                log.info(token);
//                chain.doFilter(req,res);
//            }else{
//                log.error("인증안됨");
//            }
//        }
        // 클라이언트의 API 요청 헤더에서 토큰 추출
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 유효성 검사 후 SecurityContext에 저장
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            System.out.println(authentication.getPrincipal());
            User user = (User) authentication.getPrincipal();
            System.out.println(user.getId());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터링
        chain.doFilter(request, response);
    }
}

//카카오는 user정보중에 general인지 teacher인지 확인할 방법이 없는데 일단 JWT안 토큰에 박아야하는지?
