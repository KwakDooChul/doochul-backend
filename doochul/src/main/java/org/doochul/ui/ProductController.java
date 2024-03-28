package org.doochul.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.application.ProductService;
import org.doochul.ui.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    private ResponseEntity<List<ProductResponse>> findProducts() {
        List<ProductResponse> response = productService.findByProducts();
        return ResponseEntity.ok(response);
    }
}
