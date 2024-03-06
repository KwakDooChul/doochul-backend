package org.doochul.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.user.UserRepository;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static org.doochul.service.LessonStatus.AFTER_LESSON;
import static org.doochul.service.LessonStatus.BEFORE_LESSON;
import static org.doochul.service.LessonStatus.END_LESSON;
import static org.doochul.service.LessonStatus.SCHEDULED_LESSON;
import static org.doochul.service.LessonStatus.WITHDRAWN_LESSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final TaskScheduler taskScheduler;
    private final MessageSendManager messageSendManager;
    private final Clock clock;
//    private final UserRepository userRepository;
//    private final LessonRepository lessonRepository;
    private final Map<Long, List<ScheduledFuture<?>>> schedule = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void initLessonNotification() {
        final List<Lesson> lessons = lessonRepository.findByStartedAtBefore(LocalDateTime.now());
        lessons.forEach(lesson -> {
            addRemindNotificationSchedule(lesson.getMemberShip().getStudent(), lesson.getMemberShip().getProduct().getTeacher(), lesson);
            addDismissalNotificationSchedule(lesson.getMemberShip().getStudent(), lesson.getMemberShip().getProduct().getTeacher(), lesson);
        });
    }

    public void applyForLesson(final LessonCreateEvent event) {
        final User student = event.student();
        final User teacher = event.teacher();
        final Lesson lesson = event.lesson();
        sendNotification(Letter.of(student, teacher, lesson.getStartedAt(), SCHEDULED_LESSON));
        addRemindNotificationSchedule(student,teacher,lesson);
        addDismissalNotificationSchedule(student,teacher,lesson);
    }

    public void withdrawnForLessons(final LessonWithdrawnEvent event) {
        sendNotification(Letter.of(event.student(), event.teacher(), event.lesson().getStartedAt(), WITHDRAWN_LESSON));
        schedule.get(event.lesson().getId())
                .forEach(ScheduledFuture -> ScheduledFuture.cancel(true));
        schedule.remove(event.lesson().getId());
    }

    public void sendNotification(final Letter letter) {
        messageSendManager.sendTo(letter);
    }

    private void addRemindNotificationSchedule(final User student, final User teacher, final Lesson lesson) {
        final LocalDateTime reminderTime = lesson.getStartedAt().minusMinutes(10);
        final Instant instant = toInstant(reminderTime);
        final ScheduledFuture<?> remindSchedule = taskScheduler.schedule(() -> sendNotification(Letter.of(student, teacher, lesson.getStartedAt(), BEFORE_LESSON)), instant);
        List<ScheduledFuture<?>> lessonSchedules = schedule.computeIfAbsent(lesson.getId(), k -> new ArrayList<>());
        lessonSchedules.add(remindSchedule);
    }

    private void addDismissalNotificationSchedule(final User student, final User teacher, final Lesson lesson) {
        final Instant instant = toInstant(lesson.getEndedAt());
        final ScheduledFuture<?> dismissalSchedule = taskScheduler.schedule(() -> deductCountAndSendNotification(student, teacher, lesson), instant);
        List<ScheduledFuture<?>> lessonSchedules = schedule.computeIfAbsent(lesson.getId(), k -> new ArrayList<>());
        lessonSchedules.add(dismissalSchedule);
    }

    private void deductCountAndSendNotification(final User student, final User teacher, final Lesson lesson) {
        final MemberShip memberShip = lesson.getMemberShip();
        memberShip.decreasedCount();
        sendNotification(Letter.of(student, teacher, lesson.getStartedAt(), AFTER_LESSON));
        if (memberShip.isCountZero()) {
            sendNotification(Letter.of(student, teacher, lesson.getStartedAt(), END_LESSON));
        }
    }

    private Instant toInstant(final LocalDateTime localDateTime) {
        return localDateTime.atZone(clock.getZone()).toInstant();
    }
}
