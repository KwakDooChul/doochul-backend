package org.doochul.infra;

import org.doochul.ui.dto.KakaoTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

@Component

public class KakaoLoginTokenClient {

    private final WebClient webClient;
    @Value("${token.uri}")
    private String TOKEN_URI;
    @Value("${redirect.uri}")
    private String REDIRECT_URI;
    @Value("${grant.type}")
    private String GRANT_TYPE;
    @Value("${client.id}")
    private String CLIENT_ID;

    public KakaoLoginTokenClient() {
        this.webClient = WebClient.create();
    }

    public KakaoTokenResponse getTokenInfo(final String code) {

        final String uri = UriComponentsBuilder.fromUriString(TOKEN_URI)
                .queryParam("grant_type", GRANT_TYPE)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("code", code)
                .toUriString();

        Flux<KakaoTokenResponse> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);

        return response.blockFirst();
    }
}
