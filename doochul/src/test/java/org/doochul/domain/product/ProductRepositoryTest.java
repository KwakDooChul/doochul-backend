package org.doochul.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("id가 일치하는 product를 반환한다.")
    @Test
    void getById() {
        //given
        final Long productId = 1L;
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";

        final User user = User.of(userId, socialId, socialType, name);
        final User savedUser = userRepository.save(user);

        final ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest("안녕", ProductType.LOL, 10);
        final Product product = Product.of(1L, savedUser, productRegisterRequest);

        final Product savedProduct = productRepository.save(product);

        //when
        final Product resultProduct = productRepository.getById(productId);

        //then
        assertThat(savedProduct.getId()).isEqualTo(resultProduct.getId());
    }
}
