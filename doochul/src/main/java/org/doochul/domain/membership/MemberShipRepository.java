package org.doochul.domain.membership;

import java.util.List;
import java.util.Optional;
import org.doochul.domain.product.Product;
import org.doochul.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {
    default MemberShip getById(final Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저는 없습니다."));
    }

    Optional<MemberShip> findByStudentAndProduct(final User student, final Product product);

    List<MemberShip> findByStudent(final User student);
}
