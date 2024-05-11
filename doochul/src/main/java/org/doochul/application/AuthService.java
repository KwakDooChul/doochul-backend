package org.doochul.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.infra.KakaoLoginTokenClient;
import org.doochul.infra.KakaoLoginUserClient;
import org.doochul.ui.dto.KakaoTokenResponse;
import org.doochul.ui.dto.KakaoUserInfoResponse;
import org.doochul.ui.dto.LoginRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final KakaoLoginTokenClient kakaoLoginTokenClient;
    private final KakaoLoginUserClient kakaoLoginUserClient;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public String login(final LoginRequest request) {
        final KakaoTokenResponse kakaoTokenResponse = kakaoLoginTokenClient.getTokenInfo(request.authorizationCode());
        final KakaoUserInfoResponse userInfo = kakaoLoginUserClient.getUserInfo(kakaoTokenResponse.access_token());

        final User user = userRepository.findBySocialIdAndSocialType(userInfo.id(), request.socialType())
                .orElseGet(() -> initUser(userInfo, userInfo.id()));

        return jwtProvider.createToken(user.getId());
    }

    private User initUser(final KakaoUserInfoResponse userInfo, final Long socialId) {
        final User user = User.of(socialId.toString(), userInfo.getName());
        return userRepository.save(user);
    }
}
