package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductSaveRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Long save(final Long userId, final ProductSaveRequest productSaveRequest) {
        final User user = userRepository.findById(userId).orElseThrow();
        return productRepository.save(Product.of(user, productSaveRequest)).getId();
    }
}
