package org.doochul.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/kakao")
    public String kakaoLogin() {
        return "kakaoLogin";
    }

    @GetMapping("/oauth/kakao")
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam("code") final String code) {
        final String jwtToken = authService.loginProcess(code);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + jwtToken)
                .body("JWT 토큰이 생성되었습니다.");
    }
}
