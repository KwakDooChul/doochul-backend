package org.doochul.ui.dto;

import org.doochul.domain.oauth.SocialType;
import org.doochul.domain.user.Identity;
import org.doochul.domain.user.User;

public record UserInfoResponse(
        String name,
        Long socialId,
        SocialType socialType,
        Identity identity
) {
    public static UserInfoResponse from(final User user) {
        return new UserInfoResponse(user.getName(), user.getSocialId(), user.getSocialType(), user.getIdentity());
    }
}
