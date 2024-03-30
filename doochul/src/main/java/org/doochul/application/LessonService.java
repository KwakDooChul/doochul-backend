package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.lesson.Lesson;
import org.doochul.domain.lesson.LessonRepository;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.LessonRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MemberShipRepository memberShipRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(final Long userId, final Long membershipId, final LessonRequest lessonRequest) {
        final User user = userRepository.findById(userId).orElseThrow();
        final MemberShip memberShip = memberShipRepository.findById(membershipId).orElseThrow();

        return lessonRepository.save(Lesson.of(user, memberShip.getProduct().getTeacher(),memberShip, lessonRequest.startedAt(), lessonRequest.endedAt(), lessonRequest.record())).getId();
    }
}
