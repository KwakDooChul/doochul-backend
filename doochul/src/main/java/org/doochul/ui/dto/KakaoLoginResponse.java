package org.doochul.ui.dto;

import org.doochul.domain.oauth.token.Token;
import org.doochul.domain.user.User;

public record KakaoLoginResponse(
        Long userId,
        String nickname,
        String profileImgUrl,
        Token accessToken
) {
    public static KakaoLoginResponse from(final User user, final Token accessToken) {
        return new KakaoLoginResponse(
                user.getId(), user.getName(), user.getProfileImgUrl(), accessToken);
    }
}
