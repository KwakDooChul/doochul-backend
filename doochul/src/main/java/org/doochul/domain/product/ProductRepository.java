package org.doochul.domain.product;

import org.doochul.common.exception.BackEndApplicationException;
import org.doochul.common.exception.ErrorCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    default Product getById(final Long id) {
        return findById(id).orElseThrow(() ->
                new BackEndApplicationException(ErrorCodes.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
