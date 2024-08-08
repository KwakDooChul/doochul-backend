package org.doochul.application.client;

import org.doochul.domain.oauth.SocialType;
import org.doochul.ui.dto.UserInfo;

public interface LoginClient {

    String requestToken(final String authCode);

    UserInfo findUserInfo(final String accessToken);

    SocialType getSocialType();
}
