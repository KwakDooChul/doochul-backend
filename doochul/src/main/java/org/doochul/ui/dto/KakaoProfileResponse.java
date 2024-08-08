package org.doochul.ui.dto;

import org.doochul.domain.oauth.SocialType;

public record KakaoProfileResponse(
        Long id,
        KakaoAccount kakao_account
) {

    public String getName() {
        return kakao_account.profile().nickname();
    }

    public UserInfo toUserInfo() {
        return new UserInfo(id, SocialType.KAKAO, getName());
    }
}
