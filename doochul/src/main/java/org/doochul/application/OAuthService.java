package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.oauth.SocialType;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.oauth.token.Jwt;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.UserInfo;
import org.doochul.ui.dto.LoginRequest;
import org.doochul.ui.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final LoginClients loginClients;

    public LoginResponse login(final String socialType, final LoginRequest request) {
        final SocialType type = SocialType.from(socialType);

        final UserInfo userInfo = loginClients.findUserInfo(type, request.authorizationCode());

        final User user = userRepository.findBySocialIdAndSocialType(userInfo.socialId(), type)
                .orElseGet(() -> initUser(userInfo, type, userInfo.socialId()));
        final Jwt accessToken = jwtProvider.createToken(user.getId());
        return LoginResponse.from(user, accessToken);
    }

    private User initUser(final UserInfo userInfo, final SocialType type, final Long socialId) {
        final User user = User.of(socialId, type, userInfo.nickname());
        return userRepository.save(user);
    }
}
