package org.doochul.domain.lesson;

import org.doochul.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    default Lesson getById(final Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다."));
    }
    //List<Lesson> findByUserId(Long userId);
    List<Lesson> findByStartedAtBefore(final LocalDateTime currentServerTime);

}
