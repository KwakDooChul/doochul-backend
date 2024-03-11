package org.doochul.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.RedisService;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.user.User;
import org.doochul.support.KeyGenerator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static org.doochul.service.LessonStatus.SCHEDULED_LESSON;
import static org.doochul.service.LessonStatus.WITHDRAWN_LESSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MessageSendManager messageSendManager;
    private final KeyGenerator keyGenerator;
    private final RedisService redisService;

    public void applyForLesson(final LessonCreateEvent event) {
        final User student = event.student();
        final User teacher = event.teacher();
        final Lesson lesson = event.lesson();
        sendNotification(
                Letter.of(student.getDeviceToken(),
                        student.getName(),
                        teacher.getName(),
                        lesson.getStartedAt(),
                        SCHEDULED_LESSON));
    }

    public void withdrawnForLessons(final LessonWithdrawnEvent event) {
        sendNotification(
                Letter.of(event.student().getDeviceToken(),
                        event.student().getName(),
                        event.teacher().getName(),
                        event.lesson().getStartedAt(),
                        WITHDRAWN_LESSON));
    }

    @Async
    public void sendNotification(final Letter letter) {
        final String key = keyGenerator.generateAccountKey(letter.targetToken());
        if (redisService.setNX(key, "notification", Duration.ofSeconds(5))) {
            messageSendManager.sendTo(letter);
            redisService.delete(key);
        }
    }
}
