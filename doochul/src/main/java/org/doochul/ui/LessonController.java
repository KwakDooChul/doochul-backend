package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.LessonService;
import org.doochul.ui.dto.LessonRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/save/{membershipId}")
    public Long save(@AuthenticationPrincipal Long userId, @PathVariable final Long membershipId, @RequestBody final LessonRequest lessonRequest) {
        return lessonService.save(userId, membershipId, lessonRequest);
    }
}
