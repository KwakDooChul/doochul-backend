package org.doochul.ui.dto;

import org.doochul.domain.oauth.token.Jwt;
import org.doochul.domain.user.User;

public record LoginResponse(
        Long userId,
        Jwt accessToken
) {
    public static LoginResponse from(final User user, final Jwt accessToken) {
        return new LoginResponse(
                user.getId(), accessToken);
    }
}
