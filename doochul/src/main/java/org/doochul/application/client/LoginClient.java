package org.doochul.application.client;

import org.doochul.domain.oauth.SocialType;
import org.doochul.ui.dto.KakaoUserInfoResponse;

public interface LoginClient {

    String requestToken(final String authCode);

    KakaoUserInfoResponse findUserInfo(final String accessToken);

    SocialType getSocialType();
}
