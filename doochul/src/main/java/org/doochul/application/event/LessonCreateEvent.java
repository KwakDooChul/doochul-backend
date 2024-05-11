package org.doochul.application.event;

import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.user.User;

public record LessonCreateEvent(
        User student,
        User teacher,
        Lesson lesson
) {
}
