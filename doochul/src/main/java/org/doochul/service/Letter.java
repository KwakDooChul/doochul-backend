package org.doochul.service;

public record Letter(
        String targetToken,
        String title,
        String body
) {
//    public static Letter from(final LessonCreateEvent event) {
//        return new Letter(event.targetToken(), event.title(), event.body());
//    }
}
