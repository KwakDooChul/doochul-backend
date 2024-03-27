package org.doochul.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.application.ProductService;
import org.doochul.ui.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memberShip")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{userId}")
    private ResponseEntity<List<ProductResponse>> findMemberShipsById(@PathVariable Long userId) {
        List<ProductResponse> response = productService.findMemberShipsById(userId);
        return ResponseEntity.ok(response);
    }
}
