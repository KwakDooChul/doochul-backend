package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.LessonResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public List<LessonResponse> findMemberShipsById(Long userId) {
        List<Lesson> lessons = lessonRepository.findByUserId(userId);
        return LessonResponse.fromList(lessons);
    }

    public Long save(final Long userId, final LessonResponse lessonResponse) {
        final User user = userRepository.findById(userId).orElseThrow();
        return lessonRepository.save(Lesson.of(user,lessonResponse)).getId();
    }


}
