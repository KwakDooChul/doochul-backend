package org.doochul.service;

import org.doochul.domain.user.User;

import java.time.LocalDateTime;

public record Letter(
        String targetToken,
        String title,
        String body
) {
    public static Letter of(
            final User student,
            final User teacher,
            final LocalDateTime startedAt,
            final LessonStatus lessonStatus
    ) {
        final String message = lessonStatus.getMessage(student.getName(), startedAt, teacher.getName());
        return new Letter(student.getDeviceToken(),  lessonStatus.getTitle(), message);
    }
}
