package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.lesson.LessonTime;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.LessonRecordRequest;
import org.doochul.ui.dto.LessonTimeRequest;
import org.doochul.ui.dto.LessonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MemberShipRepository memberShipRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(final Long userId, final Long membershipId, final LessonTimeRequest lessonTimeRequest, final LessonRecordRequest lessonRecordRequest) {
        final User user = userRepository.getById(userId);
        final MemberShip memberShip = memberShipRepository.getById(membershipId);
        final LessonTime lessonTime = LessonTime.of(lessonTimeRequest.startedAt(), lessonTimeRequest.endedAt());
        return lessonRepository.save(Lesson.of(user, memberShip, lessonTime, lessonRecordRequest.record())).getId();
    }

    @Transactional(readOnly = true)
    public List<LessonResponse> findByLessons(final Long userId) {
        final User user = userRepository.getById(userId);
        final List<Lesson> lessons = lessonRepository.findByUser(user);
        return LessonResponse.from(lessons);
    }

    @Transactional
    public void update(final Long lessonId, final LessonTimeRequest lessonTimeRequest, final LessonRecordRequest lessonRecordRequest) {
        final Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        final LessonTime lessonTime = LessonTime.of(lessonTimeRequest.startedAt(), lessonTimeRequest.endedAt());
        lesson.update(lessonTime, lessonRecordRequest.record());
    }

    @Transactional
    public void delete(final Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
