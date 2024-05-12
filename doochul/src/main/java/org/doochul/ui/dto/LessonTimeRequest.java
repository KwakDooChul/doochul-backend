package org.doochul.ui.dto;

import java.time.LocalDateTime;

public record LessonTimeRequest(
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {
}
