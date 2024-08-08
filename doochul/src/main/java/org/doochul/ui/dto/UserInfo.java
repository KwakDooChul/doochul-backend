package org.doochul.ui.dto;

import org.doochul.domain.oauth.SocialType;

public record UserInfo(
        Long socialId,
        SocialType socialType,
        String nickname
) {
}
