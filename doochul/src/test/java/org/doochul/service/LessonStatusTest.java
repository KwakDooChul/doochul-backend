package org.doochul.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.doochul.domain.lesson.LessonStatus;
import org.junit.jupiter.api.Test;

class LessonStatusTest {

    @Test
    void testBeforeLessonMessage() {
        LocalDateTime time = LocalDateTime.now();
        LessonStatus lessonStatus = LessonStatus.BEFORE_LESSON;
        String expectedMessage = "John님 " + formatToLocalTime(time) + " Math 강사님 수업 잊지 않으셨죠?";
        String actualMessage = lessonStatus.getMessage("John", time, "Math");

        assertEquals("수업 전", lessonStatus.getTitle());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testAfterLessonMessage() {
        LocalDateTime time = LocalDateTime.now();
        LessonStatus lessonStatus = LessonStatus.AFTER_LESSON;
        String expectedMessage = "Jane님 오늘 수업 잘 받으셨나요?";
        String actualMessage = lessonStatus.getMessage("Jane", time, "English");

        assertEquals("수업 후", lessonStatus.getTitle());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testScheduledLessonMessage() {
        LocalDateTime time = LocalDateTime.now();
        LessonStatus lessonStatus = LessonStatus.SCHEDULED_LESSON;
        String expectedMessage = formatToLocalTime(time) + " Mike님 Chemistry 강사님 수업을 신청했습니다.";
        String actualMessage = lessonStatus.getMessage("Mike", time, "Chemistry");

        assertEquals("수업 신청", lessonStatus.getTitle());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testWithdrawnLessonMessage() {
        LocalDateTime time = LocalDateTime.now();
        LessonStatus lessonStatus = LessonStatus.WITHDRAWN_LESSON;
        String expectedMessage = formatToLocalTime(time) + " Alice님 Physics 강사님의 수업을 철회했습니다.";
        String actualMessage = lessonStatus.getMessage("Alice", time, "Physics");

        assertEquals("수업 철회", lessonStatus.getTitle());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testEndLessonMessage() {
        LocalDateTime time = LocalDateTime.now();
        LessonStatus lessonStatus = LessonStatus.END_LESSON;
        String expectedMessage = "History 강사님의 수업이 모두 종료되었습니다.";
        String actualMessage = lessonStatus.getMessage("Bobby", time, "History");

        assertEquals("수업 종료", lessonStatus.getTitle());
        assertEquals(expectedMessage, actualMessage);
    }

    private String formatToLocalTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("a HH시 mm분"));
    }
}
