package org.doochul.infra;

import lombok.extern.slf4j.Slf4j;
import org.doochul.service.Letter;
import org.doochul.service.MessageSendManager;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class KAKAOMessageSendManager implements MessageSendManager {
    private static final String MESSAGE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

    private final WebClient webClient;

    protected KAKAOMessageSendManager() {
        this.webClient = WebClient.create(MESSAGE_URL);
    }

    @Override
    public void sendTo(String targetToken, Letter letter) {
        webClient.post()
                .header("Authorization", "Bearer " + targetToken)
                .bodyValue(letter)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new IllegalArgumentException()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new IllegalArgumentException()))
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("알림 전송 성공 : " + response))
                .subscribe();
    }
}
