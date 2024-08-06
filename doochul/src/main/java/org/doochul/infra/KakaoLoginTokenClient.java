package org.doochul.infra;

import java.util.Objects;
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

    public String request(final String authCode) {
        final String uri = UriComponentsBuilder.fromUriString(TOKEN_URI)
                .queryParam("grant_type", GRANT_TYPE)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("code", authCode)
                .toUriString();
        System.out.println(uri);

        Flux<KakaoTokenResponse> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);

        return Objects.requireNonNull(response.blockFirst()).access_token();
    }
}
