package org.doochul.domain.lesson;

import java.util.List;
import org.doochul.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    default Lesson getById(final Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다."));
    }

    List<Lesson> findByUser(User user);
}
