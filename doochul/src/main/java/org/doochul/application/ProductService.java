package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

}
