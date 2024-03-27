package org.doochul.ui.dto;

import lombok.Getter;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.user.User;

import java.time.LocalDateTime;

@Getter
public class LessonRequest {
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String record;

}
