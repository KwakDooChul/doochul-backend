package org.doochul.service;

import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.user.User;

public record LessonCreateEvent(
        User student,
        User teacher,
        MemberShip memberShip,
        Lesson lesson
) {
}
