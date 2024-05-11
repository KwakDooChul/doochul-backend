package org.doochul.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jakarta.transaction.Transactional;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductRegisterRequest;
import org.doochul.ui.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

        final User user = User.of("123", "1234");
        userRepository.save(user);
    }

    @Test
    @Transactional
    void product_save() {
        //given
        productService.save(1L, new ProductRegisterRequest("안녕", ProductType.LOL, 10));

        //when

        //then
    }

    @Test
    @Transactional
    void product_detail() {
        //given
        productService.save(1L, new ProductRegisterRequest("안녕", ProductType.LOL, 10));
        productService.findProduct(1L);

        //when

        //then
    }

    @Test
    @Transactional
    void product_findProducts() {
        //given
        productService.save(1L, new ProductRegisterRequest("안녕", ProductType.LOL, 10));
        productService.save(1L, new ProductRegisterRequest("안녕1", ProductType.LOL, 10));

        //when
        List<ProductResponse> products = productService.findProducts();
        products.forEach(it -> System.out.println(it.name()));
        //then
        assertEquals(products.size(),2);
    }

    @Test
    @Transactional
    void product_delete() {
        //given
        productService.save(1L, new ProductRegisterRequest("안녕", ProductType.LOL, 10));
        productService.save(1L, new ProductRegisterRequest("안녕1", ProductType.LOL, 10));

        //when
        List<ProductResponse> firstProducts = productService.findProducts();

        assertEquals(firstProducts.size(),2);

        productService.deleteProduct(2L);

        List<ProductResponse> secondProduct = productService.findProducts();
        //then
        assertEquals(secondProduct.size(),1);
    }
}
