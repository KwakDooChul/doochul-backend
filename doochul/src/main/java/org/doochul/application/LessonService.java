package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.ui.dto.LessonRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MemberShipRepository memberShipRepository;

    @Transactional
    public Long save(final Long membershipId, final LessonRequest lessonRequest) {
        final MemberShip memberShip = memberShipRepository.findById(membershipId).orElseThrow();
        return lessonRepository.save(Lesson.of(memberShip, lessonRequest.getStartedAt(), lessonRequest.getEndedAt(), lessonRequest.getRecord())).getId();
    }
}
