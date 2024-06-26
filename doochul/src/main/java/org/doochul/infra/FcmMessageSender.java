package org.doochul.infra;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.infra.dto.Letter;
import org.doochul.application.MessageSendManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmMessageSender implements MessageSendManager {

    @Value("${fcm.certification.path}")
    private String FCM_CERTIFICATION_PATH;

    @PostConstruct
    public void initialize() {
        try {
            ClassPathResource resource = new ClassPathResource(FCM_CERTIFICATION_PATH);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (FileNotFoundException e) {
            log.error("파일을 찾을 수 없습니다. ", e);
        } catch (IOException e) {
            log.error("FCM 인증이 실패했습니다. ", e);
        }
    }

    @Override
    public void sendTo(final Letter letter) {
        final Notification notification = Notification.builder()
                .setTitle(letter.title())
                .setBody(letter.body())
                .build();
        final Message message = Message.builder()
                .setToken(letter.targetToken())
                .setNotification(notification)
                .build();
        try {
            final String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            log.info("알림 전송 성공 : " + response);
        } catch (InterruptedException e) {
            log.error("FCM 알림 스레드에서 문제가 발생했습니다.", e);
        } catch (ExecutionException e) {
            log.error("FCM 알림 전송에 실패했습니다.", e);
        }
    }
}