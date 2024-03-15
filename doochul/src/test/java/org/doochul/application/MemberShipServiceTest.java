package org.doochul.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.Gender;
import org.doochul.domain.user.Identity;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberShipServiceTest {

    @Autowired
    private MemberShipRepository memberShipRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void memberShip에_저장한_userId는_User에_저장한_userId와_같아야한다() {
        // Given
        User user = new User(2L, "JEON", "deviceToken1234", "password1234", Gender.MEN, Identity.GENERAL);
        User teacher = new User(1L, "Faker", "deviceToken123", "password123", Gender.MEN, Identity.TEACHER);

        userRepository.save(user);
        userRepository.save(teacher);

        Product product = new Product(1L, "페이커", ProductType.LOL, teacher, 10);
        productRepository.save(product);

        // When
        MemberShip savedMemberShip = new MemberShipService(memberShipRepository, productRepository, userRepository)
                .save(user.getId(), product.getId());

        // Then
        assertThat(user.getId()).isEqualTo(savedMemberShip.getStudent().getId());
    }
}
