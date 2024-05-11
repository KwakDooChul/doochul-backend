package org.doochul.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.application.event.LessonCreateEvent;
import org.doochul.application.event.LessonWithdrawnEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final NotificationService notificationService;

    @EventListener
    @Async
    public void scheduleLessonNotificationEvent(final LessonCreateEvent event) {
        notificationService.applyForLesson(event);
    }

    @EventListener
    @Async
    public void withdrawnLessonNotificationEvent1(final LessonWithdrawnEvent event) {
        notificationService.withdrawnForLessons(event);
    }
}
