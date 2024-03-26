package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.UserService;
import org.doochul.domain.oauth.jwt.JwtTokenProvider;
import org.doochul.domain.oauth.kakao.KakaoTokenJsonData;
import org.doochul.domain.oauth.kakao.KakaoUserInfo;
import org.doochul.ui.dto.KakaoTokenResponse;
import org.doochul.ui.dto.KakaoUserInfoResponse;
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

    @GetMapping("/oauth/kakao")
    @ResponseBody
    public ResponseEntity<String> kakaoOauth(@RequestParam("code") String code) {
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);

        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());

        String socialId = userService.createUser(userInfo.getId().toString(),
                userInfo.getKakao_account().getProfile().getNickname());

        String jwtToken = jwtTokenProvider.createToken(socialId);

        log.info("JWT 정보 : {} ", jwtToken);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + jwtToken)
                .body("JWT 토큰이 생성되었습니다.");
    }
}
