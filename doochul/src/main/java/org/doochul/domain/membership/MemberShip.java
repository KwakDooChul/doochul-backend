package org.doochul.domain.membership;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doochul.domain.BaseEntity;
import org.doochul.domain.product.Product;
import org.doochul.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberShip extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer remainingCount;

    public void decreasedCount() {
        validateMinRemainingCount();
        remainingCount -= 1;
    }

    public boolean isCountZero() {
        return remainingCount == 0;
    }

    private void validateMinRemainingCount() {
        if (remainingCount < 1) {
            throw new IllegalArgumentException("안돼");
        }
    }

    private MemberShip(Long id, User student, Product product, Integer remainingCount) {
        this.id = id;
        this.student = student;
        this.product = product;
        this.remainingCount = remainingCount;
    }

    public static MemberShip of(User student, Product product, Integer remainingCount) {
        return new MemberShip(null, student, product, remainingCount);
    }
}
