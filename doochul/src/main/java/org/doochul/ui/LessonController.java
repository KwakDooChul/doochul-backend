package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.LessonService;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.user.User;

import org.doochul.ui.dto.LessonRequest;
import org.doochul.ui.dto.LessonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    @PostMapping("/save/{membershipId}")
    public Long save(final @PathVariable Long membershipId, final @RequestBody LessonRequest lessonRequest) {
        return lessonService.save(membershipId,lessonRequest);
    }

}
