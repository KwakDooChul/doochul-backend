package org.doochul.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.oauth.token.Token;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.infra.KakaoLoginTokenClient;
import org.doochul.infra.KakaoLoginUserClient;
import org.doochul.ui.dto.KakaoAccount;
import org.doochul.ui.dto.KakaoAccount.Profile;
import org.doochul.ui.dto.KakaoLoginResponse;
import org.doochul.ui.dto.KakaoTokenResponse;
import org.doochul.ui.dto.KakaoUserInfoResponse;
import org.doochul.ui.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OAuthServiceTest {

    @Mock
    private KakaoLoginTokenClient kakaoLoginTokenClient;

    @Mock
    private KakaoLoginUserClient kakaoLoginUserClient;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OAuthService oAuthService;

    @DisplayName("성공 - 카카오 로그인 테스트")
    @Test
    public void kakaoLogin() {
        //given
        final String authorizationCode = "auth_code";
        final String socialType = "kakao";
        final LoginRequest request = new LoginRequest(socialType, authorizationCode);

        final KakaoTokenResponse kakaoTokenResponse = new KakaoTokenResponse(
                "access_token",
                "bearer",
                "refresh_token",
                5184000,
                43199);

        final KakaoAccount kakaoAccount = new KakaoAccount(new Profile("카카오 유저 1"));
        final KakaoUserInfoResponse kakaoUserInfoResponse = new KakaoUserInfoResponse(
                3411L, "2022-04-11T01:45:28Z", kakaoAccount);

        final User existingUser = User.of(3411L, socialType, "카카오 유저 1");

        Token token = new Token("jwt_token", LocalDateTime.now());

        when(kakaoLoginTokenClient.getTokenInfo(authorizationCode)).thenReturn(kakaoTokenResponse);
        when(kakaoLoginUserClient.getUserInfo(kakaoTokenResponse.access_token())).thenReturn(kakaoUserInfoResponse);
        when(userRepository.findBySocialIdAndSocialType(kakaoUserInfoResponse.id(), socialType)).thenReturn(
                Optional.of(existingUser));
        when(jwtProvider.createToken(existingUser.getId())).thenReturn(token);

        //when
        KakaoLoginResponse response = oAuthService.kakaoLogin(request);

        //then
        assertThat(existingUser.getName()).isEqualTo(response.name());
        assertThat(token.getToken()).isEqualTo(response.accessToken().getToken());

        verify(kakaoLoginTokenClient, times(1)).getTokenInfo(authorizationCode);
        verify(kakaoLoginUserClient, times(1)).getUserInfo(kakaoTokenResponse.access_token());
        verify(userRepository, times(1)).findBySocialIdAndSocialType(kakaoUserInfoResponse.id(), socialType);
        verify(jwtProvider, times(1)).createToken(existingUser.getId());
    }
}
