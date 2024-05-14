package org.doochul.domain.lesson;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
public class LessonTime {

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    protected LessonTime(final LocalDateTime startedAt, final LocalDateTime endedAt) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public static LessonTime of(final LocalDateTime startedAt, final LocalDateTime endedAt) {
        return new LessonTime(startedAt, endedAt);
    }
}
