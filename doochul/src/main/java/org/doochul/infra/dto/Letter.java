package org.doochul.infra.dto;

import org.doochul.domain.lesson.LessonStatus;

import java.time.LocalDateTime;

public record Letter(
        String targetToken,
        String title,
        String body
) {
    public static Letter of(
            final String studentToken,
            final String studentName,
            final String teacherName,
            final LocalDateTime startedAt,
            final LessonStatus lessonStatus
    ) {
        final String message = lessonStatus.getMessage(studentName, startedAt, teacherName);
        return new Letter(studentToken,  lessonStatus.getTitle(), message);
    }
}
