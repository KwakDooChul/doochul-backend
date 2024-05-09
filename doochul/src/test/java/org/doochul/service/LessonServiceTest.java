package org.doochul.service;

import org.doochul.application.RedisService;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class LessonServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Test
    void lessonService_save() throws InterruptedException {
        // Given
        User user = User.of("123", "1234");
        User teacher = User.of("1234", "123");

        userRepository.save(user);
        userRepository.save(teacher);

        Product product = Product.of(teacher, new ProductRegisterRequest("페이커", ProductType.LOL, 10));
        productRepository.save(product);


    }
}
