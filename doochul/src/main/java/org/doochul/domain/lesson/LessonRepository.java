package org.doochul.domain.lesson;

import java.util.List;
import java.util.Optional;
import org.doochul.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    default Lesson getById(final Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다."));
    }

    Optional<Lesson> findByUser(final User user);

    Optional<Lesson> findByTeacher(final User teacher);

    List<Lesson> findAllByUser(final User user);

    List<Lesson> findAllByTeacher(final User teacher);
}
