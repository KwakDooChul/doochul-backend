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
import org.doochul.common.exception.BackEndApplicationException;
import org.doochul.common.exception.ErrorCodes;
import org.doochul.domain.BaseEntity;
import org.doochul.domain.user.User;
import org.doochul.ui.dto.ProductCreateRequest;
import org.doochul.ui.dto.ProductUpdateRequest;
import org.springframework.http.HttpStatus;

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

    private Product(
            final Long id,
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

    private Product(
            final String name,
            final ProductType type,
            final User teacher,
            final Integer count
    ) {
        this.name = name;
        this.type = type;
        this.teacher = teacher;
        this.count = count;
    }

    public static Product of(final User user, final ProductCreateRequest productCreateRequest) {
        return new Product(productCreateRequest.name(), productCreateRequest.type(), user,
                productCreateRequest.count());
    }

    public static Product of(final Long id, final User user, final ProductCreateRequest productCreateRequest) {
        return new Product(id, productCreateRequest.name(), productCreateRequest.type(), user,
                productCreateRequest.count());
    }

    public void update(final User user, final ProductUpdateRequest productUpdateRequest) {
        verifyOwner(user);
        this.name = productUpdateRequest.name();
        this.type = ProductType.from(productUpdateRequest.type());
        this.count = productUpdateRequest.count();
    }

    private void verifyOwner(User user) {
        if (!teacher.getId().equals(user.getId())) {
            throw new BackEndApplicationException(ErrorCodes.PRODUCT_VERIFY_OWNER,
                    HttpStatus.FORBIDDEN);
        }
    }

    public String getTeacherName() {
        return teacher.getName();
    }
}
