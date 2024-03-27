package org.doochul.ui.dto;

import lombok.Builder;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record LessonResponse(
        Long id,
        MemberShip memberShip,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String record
) {
    public static LessonResponse from(final Lesson lesson) {
        return new LessonResponse(lesson.getId(), lesson.getMemberShip(), lesson.getStartedAt(), lesson.getEndedAt(), lesson.getRecord());
    }

    public static List<LessonResponse> fromList(final List<Lesson> lessons) {
        return lessons.stream()
                .map(LessonResponse::from)
                .toList();
    }
}
