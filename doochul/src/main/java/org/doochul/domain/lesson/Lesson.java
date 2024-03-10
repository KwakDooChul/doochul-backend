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
import org.doochul.domain.user.User;

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

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private String record;

    public Lesson(
            final MemberShip memberShip,
            final User student,
            final User teacher,
            final LocalDateTime startedAt,
            final LocalDateTime endedAt
    ) {
        this.memberShip = memberShip;
        this.student = student;
        this.teacher = teacher;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.record = null;
    }
}
