package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.LessonService;
import org.doochul.resolver.AuthenticationPrincipal;
import org.doochul.ui.dto.LessonRecordRequest;
import org.doochul.ui.dto.LessonTimeRequest;
import org.doochul.ui.dto.LessonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/save/memberships/{membershipId}")
    public Long save(@AuthenticationPrincipal Long userId, @PathVariable final Long membershipId, @RequestBody final LessonTimeRequest lessonTimeRequest, @RequestBody final LessonRecordRequest lessonRecordRequest) {
        return lessonService.save(userId, membershipId, lessonTimeRequest, lessonRecordRequest);
    }

    @GetMapping("/lessons")
    public ResponseEntity<List<LessonResponse>> findLessons() {
        final List<LessonResponse> response = lessonService.findByLessons();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{lessonId}")
    public ResponseEntity<Void> update(@PathVariable final Long lessonId, @RequestBody final LessonTimeRequest lessonTimeRequest, @RequestBody final LessonRecordRequest lessonRecordRequest) {
        lessonService.update(lessonId, lessonTimeRequest, lessonRecordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<Void> delete(@PathVariable final Long lessonId) {
        lessonService.delete(lessonId);
        return ResponseEntity.noContent().build();
    }
}
