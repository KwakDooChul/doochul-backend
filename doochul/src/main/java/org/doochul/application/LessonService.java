package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.LessonRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MemberShipRepository memberShipRepository;

    @Transactional
    // request 받아서 save 하는 로직
    public Long save(final Long membershipId, final LessonRequest lessonRequest) {
        final MemberShip memberShip = memberShipRepository.findById(membershipId).orElseThrow();
        return lessonRepository.save(Lesson.of(memberShip, lessonRequest.getStartedAt(), lessonRequest.getEndedAt(), lessonRequest.getRecord())).getId();
    }


}
