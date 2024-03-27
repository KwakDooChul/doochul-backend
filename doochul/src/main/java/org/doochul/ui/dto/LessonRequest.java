package org.doochul.ui.dto;

import lombok.Getter;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.user.User;

import java.time.LocalDateTime;

public record LessonRequest(
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String record
) {

}
