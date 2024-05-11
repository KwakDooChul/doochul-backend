package org.doochul.ui;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.AuthService;
import org.doochul.ui.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/oauth/kakao")
    @ResponseBody
    public ResponseEntity<String> login(final LoginRequest loginRequest, HttpServletResponse response) {
        final String jwtToken = authService.login(loginRequest);

        Cookie cookie = new Cookie("Authorization", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60);

        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK)
                .body("JWT 토큰이 생성되었습니다.");
    }
}
