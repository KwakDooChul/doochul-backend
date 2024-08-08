package org.doochul.ui.dto;

import java.time.LocalDateTime;

public record LessonCreateRequest(
        String record,
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {
}
