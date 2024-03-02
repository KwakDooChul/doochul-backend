package org.doochul.domain.lesson;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.doochul.domain.BaseEntity;
import org.doochul.domain.membership.MemberShip;

import java.time.LocalDateTime;

public class Lesson extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private MemberShip memberShip;

    private String record;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;
}
