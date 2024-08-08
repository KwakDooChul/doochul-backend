package org.doochul.domain.lesson;

import io.lettuce.core.dynamic.annotation.Param;
import org.doochul.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    default Lesson getById(final Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다."));
    }

    @Query("SELECT l FROM Lesson l " +
            "JOIN FETCH l.user u " +
            "JOIN FETCH l.teacher t " +
            "WHERE l.lessonTime.startedAt <= :startAt")
    List<Lesson> findLessonsByStart(@Param("startAt") final LocalDateTime startAt);

    @Query("SELECT l FROM Lesson l " +
            "JOIN FETCH l.user u " +
            "JOIN FETCH l.teacher t " +
            "WHERE l.lessonTime.endedAt <= :endAt")
    List<Lesson> findLessonsByEnd(@Param("endAt") final LocalDateTime endAt);

    List<Lesson> findAllByUser(final User user);

    List<Lesson> findAllByTeacher(final User teacher);
}
