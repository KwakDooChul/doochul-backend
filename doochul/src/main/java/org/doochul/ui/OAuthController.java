package org.doochul.ui;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.OAuthService;
import org.doochul.ui.dto.LoginRequest;
import org.doochul.ui.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthService OAuthService;

    @PostMapping("/oauth/login")
    public ResponseEntity<String> login(
            @RequestBody final LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        final LoginResponse kakaoResponse = OAuthService.login(loginRequest);
        String token = kakaoResponse.accessToken().token();

        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK)
                .body("JWT 토큰이 쿠키에 저장되었습니다.");
    }

    @PostMapping("/oauth/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 쿠키 제거
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK)
                .body("로그아웃되었습니다.");
    }
}
