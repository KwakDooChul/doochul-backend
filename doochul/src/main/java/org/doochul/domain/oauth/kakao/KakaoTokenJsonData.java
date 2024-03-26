package org.doochul.domain.oauth.kakao;

import org.doochul.ui.dto.KakaoTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class KakaoTokenJsonData {

    private final WebClient webClient;
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth/kakao";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String CLIENT_ID = "2c577a66aabe83405bfba16807e8495b";

    public KakaoTokenJsonData() {
        this.webClient = WebClient.create();
    }

    public KakaoTokenResponse getToken(String code) {

        String uri =
                TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI
                        + "&code=" + code;

        Flux<KakaoTokenResponse> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);

        return response.blockFirst();
    }
}
