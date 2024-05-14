package org.doochul.ui.dto;

import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record LessonResponse(
        Long id,
        MemberShip memberShip,
        User user,
        User teacher,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String record
) {
    public static LessonResponse from(final Lesson lesson) {
        return new LessonResponse(lesson.getId(), lesson.getMemberShip(), lesson.getUser()
                , lesson.getTeacher(), lesson.getStartedAt(), lesson.getEndedAt(), lesson.getRecord());
    }

    public static List<LessonResponse> to(final List<Lesson> lessons) {
        return lessons.stream()
                .map(LessonResponse::from)
                .toList();
    }
}
