package org.doochul.ui.dto;

import java.util.List;
import org.doochul.domain.lesson.Lesson;

public record LessonResponse(
        Long id,
        String productName,
        String user,
        String teacher,
        String record
) {
    public static LessonResponse from(final Lesson lesson) {
        return new LessonResponse(lesson.getId(), lesson.getProductName(), lesson.getUserName(),
                lesson.getTeacherName(), lesson.getRecord());
    }

    public static List<LessonResponse> from(final List<Lesson> lessons) {
        return lessons.stream()
                .map(LessonResponse::from)
                .toList();
    }
}
