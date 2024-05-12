package org.doochul.domain.lesson;

import jakarta.persistence.*;
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private MemberShip memberShip;

    @Embedded
    private LessonTime lessonTime;

    private String record;

    public Lesson(final Long id, final User user, final User teacher, final MemberShip memberShip, final LessonTime lessonTime, final String record) {
        this.id = id;
        this.user = user;
        this.teacher = teacher;
        this.memberShip = memberShip;
        this.lessonTime = lessonTime;
        this.record = record;
    }

    public static Lesson of(final User user, final MemberShip memberShip, final LessonTime lessonTime, final String record) {
        return new Lesson(null, user, memberShip.getTeacher(), memberShip, lessonTime, record);
    }

    public void update(final LessonTime lessonTime, final String record) {
        this.lessonTime = lessonTime;
        this.record = record;
    }
}
