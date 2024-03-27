package org.doochul.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.ui.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> findMemberShipsById(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);
        return ProductResponse.fromList(products);
    }
}
