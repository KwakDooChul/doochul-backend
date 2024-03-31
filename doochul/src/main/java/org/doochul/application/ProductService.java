package org.doochul.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductRegisterRequest;
import org.doochul.ui.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductResponse> findByProducts() {
        final List<Product> products = productRepository.findAll();
        return ProductResponse.to(products);
    }

    public Long save(final Long userId, final ProductRegisterRequest productRegisterRequest) {
        final User user = userRepository.findById(userId).orElseThrow();
        final Product product = Product.of(user, productRegisterRequest);
        productRepository.save(product);
        return product.getId();
    }
}
