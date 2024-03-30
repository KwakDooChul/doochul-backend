package org.doochul.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.user.User;
import org.doochul.infra.KakaoLoginTokenClient;
import org.doochul.infra.KakaoLoginUserClient;
import org.doochul.ui.dto.KakaoTokenResponse;
import org.doochul.ui.dto.KakaoUserInfoResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final KakaoLoginTokenClient kakaoLoginTokenClient;
    private final KakaoLoginUserClient kakaoLoginUserClient;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public String loginForCreateJwt(final String code) {
        final KakaoTokenResponse kakaoTokenResponse = kakaoLoginTokenClient.getTokenInfo(code);

        final KakaoUserInfoResponse userInfo = kakaoLoginUserClient.getUserInfo(kakaoTokenResponse.access_token());

        final Long socialId = userInfo.id();

        final Optional<User> user = userService.findUserBySocialId(socialId);

        if (user.isEmpty()) {
            String nickname = userInfo.kakao_account().profile().nickname();
            userService.createUser(socialId.toString(), nickname);
        }

        final String jwtToken = jwtProvider.createToken(socialId.toString());

        log.info("JWT 정보 : {} ", jwtToken);

        return jwtToken;
    }
}
