package org.doochul.ui;

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
        response.setHeader("Authorization", "Bearer " + kakaoResponse.accessToken().token());
        return ResponseEntity.status(HttpStatus.OK)
                .body("JWT 토큰이 생성되었습니다.");
    }
}
