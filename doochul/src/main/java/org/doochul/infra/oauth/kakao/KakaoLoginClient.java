package org.doochul.infra.oauth.kakao;

import lombok.RequiredArgsConstructor;
import org.doochul.application.client.LoginClient;
import org.doochul.domain.oauth.SocialType;
import org.doochul.infra.KakaoLoginTokenClient;
import org.doochul.infra.KakaoLoginUserClient;
import org.doochul.ui.dto.KakaoUserInfoResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoLoginClient implements LoginClient {

    private final KakaoLoginTokenClient kakaoLoginTokenClient;
    private final KakaoLoginUserClient kakaoLoginUserClient;

    @Override
    public String requestToken(final String authCode) {
        return kakaoLoginTokenClient.request(authCode);
    }

    @Override
    public KakaoUserInfoResponse findUserInfo(final String accessToken) {
        return kakaoLoginUserClient.getUserInfo(accessToken);
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }
}
