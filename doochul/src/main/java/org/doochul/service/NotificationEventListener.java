package org.doochul.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.doochul.service.LessonStatus.AFTER_LESSON;
import static org.doochul.service.LessonStatus.BEFORE_LESSON;
import static org.doochul.service.LessonStatus.END_LESSON;
import static org.doochul.service.LessonStatus.SCHEDULED_LESSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final TaskScheduler taskScheduler;
    private final MessageSendManager messageSendManager;
    private final Clock clock;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @EventListener
    @Async
    public void handleNotificationEvent(final LessonCreateEvent event) {
        final Lesson lesson = event.lesson();
        final User user = event.user();
        final String userName = user.getName();
        final String lessonName = lesson.getName();
        final LocalDateTime startedAt = lesson.getStartedAt();
        final LocalDateTime endedAt = lesson.getEndedAt();
        sendNotification(userName,lessonName,startedAt, SCHEDULED_LESSON);
        addRemindNotificationSchedule(userName,lessonName,startedAt);
        addDismissalNotificationSchedule(userName,lessonName,endedAt);
    }

//    @EventListener
//    @Async
//    public void handleNotificationEvent1(final LessonWithdrawnEvent event) {
//        final Lesson lesson = event.lesson();
//        final User user = event.user();
//        final String userName = user.getName();
//        final String lessonName = lesson.getName();
//        final LocalDateTime startedAt = lesson.getStartedAt();
//        final LocalDateTime endedAt = lesson.getEndedAt();
//        sendNotification(userName,lessonName,startedAt);
//        addRemindNotificationSchedule(userName,lessonName,startedAt);
//        addDismissalNotificationSchedule(userName,lessonName,endedAt);
//    }

    public void sendNotification(final Letter letter) {
        messageSendManager.sendMessageTo(letter,SCHEDULED_LESSON);
    }

    private void addRemindNotificationSchedule(final LocalDateTime startedAt) {
        final LocalDateTime reminderTime = startedAt.minusMinutes(10);
        final Instant instant = toInstant(reminderTime);
        taskScheduler.schedule(() -> sendNotification(letter, BEFORE_LESSON), instant);
    }

    private void addDismissalNotificationSchedule(final LocalDateTime endedAt) {
        final Instant instant = toInstant(endedAt);
        taskScheduler.schedule(() -> deductCountAndSendNotification(), instant);
    }

    private void deductCountAndSendNotification(Lesson lesson) {
        final MemberShip memberShip = lesson.getMemberShip();
        memberShip.deductCount();
        sendNotification(letter, AFTER_LESSON);
        if (memberShip.isCountZero()) {
            sendNotification(letter, END_LESSON);
        }
    }

    private Instant toInstant(final LocalDateTime localDateTime) {
        return localDateTime.atZone(clock.getZone()).toInstant();
    }
}
