package org.doochul.infra;

import org.doochul.ui.dto.KakaoUserInfoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class KakaoLoginUserClient {

    private final WebClient webClient;
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public KakaoLoginUserClient() {
        this.webClient = WebClient.create(USER_INFO_URI);
    }

    public KakaoUserInfoResponse getUserInfo(final String token) {
        Flux<KakaoUserInfoResponse> response = webClient.get()
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);
        return response.blockFirst();
    }
}
