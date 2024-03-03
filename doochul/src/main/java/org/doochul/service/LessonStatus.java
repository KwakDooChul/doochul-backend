package org.doochul.service;

public enum LessonStatus {
    BEFORE_LESSON("***님 오전 ##:## ***강사님 수업 잊지 않으셨죠?"),
    AFTER_LESSON("***님 오늘 수업 잘 받으셨나요?"),
    SCHEDULED_LESSON("오전 ##:## ***강사님 수업을 신청했습니다."),
    WITHDRAWN_LESSON("오전 ##:## ***강사님의 수업을 철회했습니다."),
    END_LESSON("***강사님의 수업이 모두 종료되었습니다.");
}
