package org.doochul.domain.lesson;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doochul.domain.BaseEntity;
import org.doochul.domain.membership.MemberShip;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private MemberShip memberShip;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private String record;

    public Lesson(Long id, MemberShip memberShip, LocalDateTime startedAt, LocalDateTime endedAt, String record) {
        this.id = id;
        this.memberShip = memberShip;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.record = record;
    }

    public static Lesson of(MemberShip memberShip, LocalDateTime startedAt, LocalDateTime endedAt, String record) {
        return new Lesson(null, memberShip, startedAt, endedAt, record);
    }
}
