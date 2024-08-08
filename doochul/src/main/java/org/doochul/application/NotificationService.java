package org.doochul.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.event.LessonCreateEvent;
import org.doochul.application.event.LessonWithdrawnEvent;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.user.User;
import org.doochul.infra.dto.Letter;
import org.doochul.support.KeyGenerator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.doochul.domain.lesson.LessonStatus.END_LESSON;
import static org.doochul.domain.lesson.LessonStatus.SCHEDULED_LESSON;
import static org.doochul.domain.lesson.LessonStatus.WITHDRAWN_LESSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MessageSendManager messageSendManager;
    private final KeyGenerator keyGenerator;
    private final LessonRepository lessonRepository;
    private final RedisService redisService;

    public void applyForLesson(final LessonCreateEvent event) {
        final User student = event.student();
        final User teacher = event.teacher();
        final Lesson lesson = event.lesson();
        sendNotification(
                Letter.of(student.getDeviceToken(),
                        student.getName(),
                        teacher.getName(),
                        lesson.getStartedTime(),
                        SCHEDULED_LESSON));
    }

    public void withdrawnForLessons(final LessonWithdrawnEvent event) {
        sendNotification(
                Letter.of(event.student().getDeviceToken(),
                        event.student().getName(),
                        event.teacher().getName(),
                        event.lesson().getStartedTime(),
                        WITHDRAWN_LESSON));
    }

    public void sendStartLesson() {
        lessonRepository.findLessonsByStart(LocalDateTime.now()).stream()
                .map(it -> Letter.of(it.getUser().getDeviceToken(),
                        it.getUserName(),
                        it.getTeacherName(),
                        it.getStartedTime(),
                        SCHEDULED_LESSON))
                .toList()
                .forEach(this::sendNotification);
    }

    public void sendEndLesson() {
        lessonRepository.findLessonsByEnd(LocalDateTime.now()).stream()
                .map(it -> Letter.of(it.getUser().getDeviceToken(),
                        it.getUserName(),
                        it.getTeacherName(),
                        it.getStartedTime(),
                        END_LESSON))
                .toList()
                .forEach(this::sendNotification);
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
