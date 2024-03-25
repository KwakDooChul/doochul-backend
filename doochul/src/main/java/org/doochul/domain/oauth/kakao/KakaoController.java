package org.doochul.domain.oauth.kakao;

import jakarta.servlet.http.Cookie;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.UserService;
import org.doochul.domain.oauth.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/index")
    public String index() {
        return "kakaoLogin.html";
    }

    @GetMapping("/oauth/kakao")
    @ResponseBody
    public ResponseEntity<String> kakaoOauth(@RequestParam("code") String code) {
        System.out.println(code);
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        System.out.println(kakaoTokenResponse);
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
        String socialId = userService.createUser(userInfo.getId().toString(),
                userInfo.getKakao_account().getProfile().getNickname()); // 이게 keyCode

        String jwtToken = jwtTokenProvider.createToken(socialId);

        log.info("JWT 정보 : {} ", jwtToken);

        // JWT 토큰을 헤더에 포함시켜 응답을 반환
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + jwtToken)
                .body("JWT 토큰이 생성되었습니다.");

//        Cookie cookie = new Cookie("jwtToken", jwtToken);
//        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        return ResponseEntity.ok("JWT 토큰이 쿠키에 저장되었습니다.");
    }
}

// createToken으로 클라이언트한테 던져주고, 그 토큰으로 다시 요청이오면 토큰의 유효성을 확인한 후, 그 토큰에서 id를 꺼내 사용한다.
