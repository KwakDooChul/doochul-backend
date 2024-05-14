package org.doochul.service;

import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LessonServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void lessonService_save() {
        // Given
        User user = User.of("123", "1234");
        User teacher = User.of("1234", "123");

        userRepository.save(user);
        userRepository.save(teacher);

        Product product = Product.of(teacher, new ProductRegisterRequest("페이커", ProductType.LOL, 10));
        productRepository.save(product);


    }
}
