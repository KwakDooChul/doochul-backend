package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.oauth.token.Token;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.infra.KakaoLoginTokenClient;
import org.doochul.infra.KakaoLoginUserClient;
import org.doochul.ui.dto.KakaoLoginResponse;
import org.doochul.ui.dto.KakaoTokenResponse;
import org.doochul.ui.dto.KakaoUserInfoResponse;
import org.doochul.ui.dto.LoginRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final KakaoLoginTokenClient kakaoLoginTokenClient;
    private final KakaoLoginUserClient kakaoLoginUserClient;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public KakaoLoginResponse kakaoLogin(final LoginRequest request) {
        final KakaoTokenResponse kakaoTokenResponse = kakaoLoginTokenClient.getTokenInfo(request.authorizationCode());
        final KakaoUserInfoResponse userInfo = kakaoLoginUserClient.getUserInfo(kakaoTokenResponse.access_token());

        final User user = userRepository.findBySocialIdAndSocialType(userInfo.id(), request.socialType())
                .orElseGet(() -> initUser(userInfo, request.socialType(), userInfo.id()));
        final Token accessToken = jwtProvider.createToken(user.getId());

        return KakaoLoginResponse.from(user, accessToken);
    }

    private User initUser(final KakaoUserInfoResponse userInfo, final String socialType, final Long socialId) {
        final User user = User.of(socialId, socialType, userInfo.getName());
        return userRepository.save(user);
    }
}
