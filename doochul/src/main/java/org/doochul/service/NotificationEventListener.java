package org.doochul.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void handleNotificationEvent(final LessonCreateEvent event) {
        notificationService.applyForLessons(event);
    }

    @EventListener
    @Async
    public void handleNotificationEvent1(final LessonWithdrawnEvent event) {
        notificationService.withdrawnForLessons(event);
    }
}
