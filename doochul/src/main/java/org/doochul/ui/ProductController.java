package org.doochul.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.doochul.application.ProductService;
import org.doochul.ui.dto.ProductSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Void> save(final Long userId, @RequestBody final ProductSaveRequest productSaveRequest) {
        Long productId = productService.save(userId, productSaveRequest);
        return ResponseEntity.created(URI.create("/product/" + productId)).build();
    }
}
