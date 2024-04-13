package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.LessonService;
import org.doochul.resolver.AuthenticationPrincipal;
import org.doochul.ui.dto.LessonRequest;
import org.doochul.ui.dto.LessonResponse;
import org.doochul.ui.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/save/{membershipId}")
    public Long save(@AuthenticationPrincipal Long userId, @PathVariable final Long membershipId, @RequestBody final LessonRequest lessonRequest) {
        return lessonService.save(userId, membershipId, lessonRequest);
    }

    @GetMapping("/lessons")
    public ResponseEntity<List<LessonResponse>> findLessons() {
        final List<LessonResponse> response = lessonService.findByLessons();
        return ResponseEntity.ok(response);
    }
}
