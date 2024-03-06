package org.doochul.service;

import org.doochul.application.RedisService;
import org.doochul.support.KeyGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.BDDMockito.*;

@SpringBootTest
class NotificationServiceTest {
    @MockBean
    private MessageSendManager messageSendManager;
    @Autowired
    private KeyGenerator keyGenerator;
    @Autowired
    private RedisService redisService;

    @Test
    void sendNotification() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);
        final Letter letter = new Letter("token", "안녕?", "테스트");

        willDoNothing().given(messageSendManager).sendTo(any(Letter.class));
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    final String key = keyGenerator.generateAccountKey(letter.targetToken());
                    if (redisService.setNX(key, "notification", Duration.ofSeconds(5))) {
                        messageSendManager.sendTo(letter);
                        redisService.delete(letter.targetToken());
                    }
                    latch.countDown();
                } catch (Exception e) {
                }
            });
        }

        latch.await();
        executorService.shutdown();

        then(messageSendManager).should(times(1)).sendTo(any(Letter.class));
    }
}
