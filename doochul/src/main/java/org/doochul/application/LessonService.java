package org.doochul.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.lesson.LessonTime;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.LessonCreateRequest;
import org.doochul.ui.dto.LessonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MemberShipRepository memberShipRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createLesson(final Long userId,
                             final Long membershipId,
                             final LessonCreateRequest lessonCreateRequest) {
        final User user = userRepository.getById(userId);
        final MemberShip memberShip = memberShipRepository.getById(membershipId);
        final LessonTime lessonTime = LessonTime.of(lessonCreateRequest.startedAt(), lessonCreateRequest.endedAt());
        return lessonRepository.save(Lesson.of(user, memberShip, lessonTime, lessonCreateRequest.record())).getId();
    }

    // lesson 단일 조회 만들어야 함

    @Transactional(readOnly = true)
    public List<LessonResponse> findByLessons(final Long userId) {
        final User user = userRepository.getById(userId);
        final List<Lesson> lessons = lessonRepository.findByUser(user);
        return LessonResponse.from(lessons);
    }

    @Transactional
    public void update(final Long lessonId, final LessonCreateRequest lessonCreateRequest){
        final Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        final LessonTime lessonTime = LessonTime.of(lessonCreateRequest.startedAt(), lessonCreateRequest.endedAt());
        lesson.update(lessonTime, lessonCreateRequest.record());
    }

    @Transactional
    public void delete(final Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
