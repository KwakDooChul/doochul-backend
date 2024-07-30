package org.doochul.ui;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.OAuthService;
import org.doochul.ui.dto.KakaoLoginResponse;
import org.doochul.ui.dto.LoginRequest;
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

    @PostMapping("/oauth/kakao")
    public ResponseEntity<String> login(
            @RequestBody final LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        final KakaoLoginResponse kakaoResponse = OAuthService.kakaoLogin(loginRequest);

        response.setHeader("Authorization", "Bearer " + kakaoResponse.accessToken().getToken());
        response.setHeader("Authorization-token-expired-at", kakaoResponse.accessToken().getExpiredAt().toString());

        return ResponseEntity.status(HttpStatus.OK)
                .body("JWT 토큰이 생성되었습니다.");
    }
}
