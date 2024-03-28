package org.doochul.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.oauth.jwt.JwtTokenProvider;
import org.doochul.infra.KakaoLoginTokenClient;
import org.doochul.infra.KakaoLoginUserClient;
import org.doochul.ui.dto.KakaoTokenResponse;
import org.doochul.ui.dto.KakaoUserInfoResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final KakaoLoginTokenClient kakaoLoginTokenClient;
    private final KakaoLoginUserClient kakaoLoginUserClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public String loginProcess(String code) {
        final KakaoTokenResponse kakaoTokenResponse = kakaoLoginTokenClient.getTokenInfo(code);

        KakaoUserInfoResponse userInfo = kakaoLoginUserClient.getUserInfo(kakaoTokenResponse.access_token());

        String socialId = userService.createUser(userInfo.id().toString(),
                userInfo.kakao_account().profile().nickname());

        String jwtToken = jwtTokenProvider.createToken(socialId);

        log.info("JWT 정보 : {} ", jwtToken);

        return jwtToken;
    }
}
