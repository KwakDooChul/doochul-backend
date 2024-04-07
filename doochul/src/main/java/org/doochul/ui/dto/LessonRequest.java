package org.doochul.ui.dto;

import java.time.LocalDateTime;

public record LessonRequest(
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String record
) {

}