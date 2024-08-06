package org.doochul.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.doochul.application.LessonService;
import org.doochul.common.resolver.AuthenticationPrincipal;
import org.doochul.ui.dto.LessonCreateRequest;
import org.doochul.ui.dto.LessonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/lesson/save/memberships/{membershipId}")
    public ResponseEntity<Long> createLesson(@AuthenticationPrincipal Long userId,
                             @PathVariable final Long membershipId,
                             @RequestBody final LessonCreateRequest lessonCreateRequest){
        final Long id = lessonService.createLesson(userId, membershipId, lessonCreateRequest);
        return ResponseEntity.created(URI.create("/lessons/" + id)).build();
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<LessonResponse> readLesson(
            @AuthenticationPrincipal final Long userId,
            @PathVariable final Long lessonId
    ) {
        final LessonResponse response = lessonService.findByLesson(userId, lessonId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lessons")
    public ResponseEntity<List<LessonResponse>> readLessons(@AuthenticationPrincipal final Long userId) {
        final List<LessonResponse> response = lessonService.findByLessons(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/lesson/{lessonId}")
    public ResponseEntity<Void> update(@PathVariable final Long lessonId, @RequestBody final LessonCreateRequest lessonCreateRequest) {
        lessonService.update(lessonId, lessonCreateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lesson/{lessonId}")
    public ResponseEntity<Void> delete(@PathVariable final Long lessonId) {
        lessonService.delete(lessonId);
        return ResponseEntity.noContent().build();
    }
}
