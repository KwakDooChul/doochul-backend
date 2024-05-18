package org.doochul.ui;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.application.ProductService;
import org.doochul.common.resolver.AuthenticationPrincipal;
import org.doochul.ui.dto.ProductRegisterRequest;
import org.doochul.ui.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/register")
    public ResponseEntity<Long> save(
            @AuthenticationPrincipal final Long userId,
            @RequestBody final ProductRegisterRequest productRegisterRequest
    ) {
        final Long productId = productService.save(userId, productRegisterRequest);
        return ResponseEntity.created(URI.create("/product/" + productId)).build();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> findProduct(@PathVariable final Long productId) {
        final ProductResponse response = productService.findProduct(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> findProducts() {
        final List<ProductResponse> response = productService.findProducts();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
