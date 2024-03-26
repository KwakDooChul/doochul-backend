package org.doochul.domain.oauth.kakao;

import org.doochul.ui.dto.KakaoUserInfoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class KakaoUserInfo {

    private final WebClient webClient;
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public KakaoUserInfo() {
        this.webClient = WebClient.create(USER_INFO_URI);
    }

    public KakaoUserInfoResponse getUserInfo(String token) {
        Flux<KakaoUserInfoResponse> response = webClient.get()
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);
        return response.blockFirst();
    }
}
