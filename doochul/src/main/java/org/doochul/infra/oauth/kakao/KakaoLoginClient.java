package org.doochul.infra.oauth.kakao;

import lombok.RequiredArgsConstructor;
import org.doochul.application.client.LoginClient;
import org.doochul.domain.oauth.SocialType;
import org.doochul.ui.dto.UserInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoLoginClient implements LoginClient {

    private final KakaoClient kakaoClient;

    @Override
    public String requestToken(final String authCode) {
        return kakaoClient.request(authCode);
    }

    @Override
    public UserInfo findUserInfo(final String accessToken) {
        return kakaoClient.getUserInfo(accessToken);
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }
}
