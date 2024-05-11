package org.doochul.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doochul.domain.BaseEntity;
import org.doochul.domain.user.User;
import org.doochul.ui.dto.ProductRegisterRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    private Integer count;

    private Product(final Long id,
                    final String name,
                    final ProductType type,
                    final User teacher,
                    final Integer count
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.teacher = teacher;
        this.count = count;
    }

    public static Product of(final User user, final ProductRegisterRequest productRegisterRequest) {
        return new Product(null, productRegisterRequest.name(), productRegisterRequest.type(), user,
                productRegisterRequest.count());
    }
}
