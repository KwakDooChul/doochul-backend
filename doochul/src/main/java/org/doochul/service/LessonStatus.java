package org.doochul.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FunctionalInterface
interface LessonStatusMessage {
    String getMessage(String name, LocalDateTime time, String teacher);
}

public enum LessonStatus {
    BEFORE_LESSON("수업 전",
            (name, time, teacher) -> name + "님 " + formatToLocalTime(time) + " " + teacher + " 강사님 수업 잊지 않으셨죠?"),
    AFTER_LESSON("수업 후", (name, time, teacher) -> name + "님 오늘 수업 잘 받으셨나요?"),
    SCHEDULED_LESSON("수업 신청",
            (name, time, teacher) -> formatToLocalTime(time) + " " + name + "님 " + teacher + " 강사님 수업을 신청했습니다."),
    WITHDRAWN_LESSON("수업 철회",
            (name, time, teacher) -> formatToLocalTime(time) + " " + name + "님 " + teacher + " 강사님의 수업을 철회했습니다."),
    END_LESSON("수업 종료", (name, time, teacher) -> teacher + " 강사님의 수업이 모두 종료되었습니다.");

    private final String title;
    private final LessonStatusMessage message;

    LessonStatus(String title, LessonStatusMessage message) {
        this.title = title;
        this.message = message;
    }

    private static String formatToLocalTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("a HH시 mm분"));
    }

    public String getTitle() {
        return title;
    }

    public String getMessage(String name, LocalDateTime time, String teacher) {
        return message.getMessage(name, time, teacher);
    }
}
