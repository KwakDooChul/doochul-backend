package org.doochul.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product getById(final Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 PT가 없습니다."));
    }
}
