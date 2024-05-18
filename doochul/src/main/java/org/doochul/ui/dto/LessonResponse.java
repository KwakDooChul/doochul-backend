package org.doochul.ui.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.membership.MemberShip;

public record LessonResponse(
        Long id,
        MemberShip memberShip,
        String user,
        String teacher,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String record
) {
    public static LessonResponse from(final Lesson lesson) {
        return new LessonResponse(lesson.getId(), lesson.getMemberShip(), lesson.getUserName(),
                lesson.getTeacherName(), lesson.getStartedTime(), lesson.getEndedTime(), lesson.getRecord());
    }

    public static List<LessonResponse> from(final List<Lesson> lessons) {
        return lessons.stream()
                .map(LessonResponse::from)
                .toList();
    }
}
