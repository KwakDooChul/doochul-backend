package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.ProductService;
import org.doochul.resolver.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memberShip")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/token")
    public String postToken(@AuthenticationPrincipal Long id) {
        return "안녕" + id;
    }
}
