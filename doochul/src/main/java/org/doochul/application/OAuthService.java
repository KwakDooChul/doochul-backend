package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.oauth.SocialType;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.oauth.token.Jwt;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.KakaoUserInfoResponse;
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

        final KakaoUserInfoResponse kakaoUserInfoResponse = loginClients.findUserInfo(type,
                request.authorizationCode());

        final User user = userRepository.findBySocialIdAndSocialType(kakaoUserInfoResponse.id(), type)
                .orElseGet(() -> initUser(kakaoUserInfoResponse, type, kakaoUserInfoResponse.id()));
        final Jwt accessToken = jwtProvider.createToken(user.getId());
        return LoginResponse.from(user, accessToken);
    }

    private User initUser(final KakaoUserInfoResponse userInfo, final SocialType type, final Long socialId) {
        final User user = User.of(socialId, type, userInfo.getName());
        return userRepository.save(user);
    }
}
