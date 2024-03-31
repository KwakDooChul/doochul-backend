package org.doochul.ui.dto;

public record KakaoUserInfoResponse(
        Long id,
        String connected_at,
        KakaoAccount kakao_account
) {

    public String getName() {
        return kakao_account.profile().nickname();
    }
}
