package org.doochul.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductCreateRequest;
import org.doochul.ui.dto.ProductResponse;
import org.doochul.ui.dto.ProductUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Long createProduct(final Long userId, final ProductCreateRequest productCreateRequest) {
        final User user = userRepository.getById(userId);
        final Product product = Product.of(user, productCreateRequest);
        final Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    public ProductResponse findProduct(final Long productId) {
        final Product product = productRepository.getById(productId);
        return ProductResponse.from(product);
    }

    public List<ProductResponse> findProducts() {
        final List<Product> products = productRepository.findAll();
        return ProductResponse.from(products);
    }

    public void updateProduct(final Long userId,
                              final ProductUpdateRequest productUpdateRequest,
                              final Long productId) {
        final User user = userRepository.getById(userId);
        final Product product = productRepository.getById(productId);
        product.update(user,productUpdateRequest);
    }

    public void deleteProduct(final Long productId) {
        productRepository.deleteById(productId);
    }
}
