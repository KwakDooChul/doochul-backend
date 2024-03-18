package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.LessonService;
import org.doochul.application.ProductService;
import org.doochul.ui.dto.LessonResponse;
import org.doochul.ui.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(final Long userId, @RequestBody LessonResponse lessonResponse) {
        Long lessonId = lessonService.save(userId, lessonResponse);
        return ResponseEntity.created(URI.create("/lesson/"+ lessonId)).build();
    }
}
