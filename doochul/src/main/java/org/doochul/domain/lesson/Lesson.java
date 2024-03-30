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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private String record;

    private Lesson(Long id, User user, User teacher, MemberShip memberShip, LocalDateTime startedAt, LocalDateTime endedAt, String record) {
        this.id = id;
        this.user = user;
        this.teacher = teacher;
        this.memberShip = memberShip;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.record = record;
    }

    public static Lesson of(User user, User teacher, MemberShip memberShip, LocalDateTime startedAt, LocalDateTime endedAt, String record) {
        return new Lesson(null, user, teacher,memberShip, startedAt, endedAt, record);
    }
}
