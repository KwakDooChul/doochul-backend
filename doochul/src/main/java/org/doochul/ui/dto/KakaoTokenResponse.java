package org.doochul.ui.dto;

public record KakaoTokenResponse(
        String access_token,
        String token_type,
        String refresh_token,
        Integer refresh_token_expires_in,
        Integer expires_in
) {
}
