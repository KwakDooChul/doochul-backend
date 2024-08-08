package org.doochul.domain.membership;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberShipRepositoryTest {

    @Autowired
    private MemberShipRepository memberShipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("학생이 PT에 대한 멤버쉽을 찾을 수 있다.")
    @Test
    void findByStudentAndProduct() {
        //given
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";
        final User user = userRepository.save(User.of(userId, socialId, socialType, name));

        final String productName = "안녕";
        final ProductType type = ProductType.LOL;
        final Integer count = 10;
        final ProductCreateRequest productCreateRequest = new ProductCreateRequest(productName, type, count);

        final Product product = productRepository.save(Product.of(1L, user, productCreateRequest));

        memberShipRepository.save(MemberShip.of(user, product, 10));

        //when
        Optional<MemberShip> result = memberShipRepository.findByStudentAndProduct(user, product);

        //then
        assertThat(result).isPresent();
        assertThat(result.get().getStudent().getName()).isEqualTo(name);
        assertThat(result.get().getProduct().getName()).isEqualTo(productName);
        assertThat(result.get().getProduct().getType()).isEqualTo(type);
        assertThat(result.get().getProduct().getCount()).isEqualTo(10);
    }

    @DisplayName("학생이 모든 멤버쉽을 찾을 수 있다.")
    @Test
    void findByStudent() {
        //given
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";
        final User user = userRepository.save(User.of(socialId, socialType, name));

        final String productName = "안녕";
        final ProductType type = ProductType.LOL;
        final Integer count = 10;

        final String productName2 = "안녕2";
        final ProductType type2 = ProductType.TFT;
        final Integer count2 = 20;

        final ProductCreateRequest productCreateRequest = new ProductCreateRequest(productName, type, count);
        final ProductCreateRequest productCreateRequest2 = new ProductCreateRequest(productName2, type2, count2);

        final Product product = productRepository.save(Product.of(user, productCreateRequest));
        final Product product2 = productRepository.save(Product.of(user, productCreateRequest2));

        memberShipRepository.save(MemberShip.of(user, product, 10));
        memberShipRepository.save(MemberShip.of(user, product2, 10));

        //when
        final List<MemberShip> result = memberShipRepository.findByStudent(user);

        //then
        assertThat(result.get(0).getStudent().getName()).isEqualTo(name);
        assertThat(result.get(0).getProduct().getName()).isEqualTo(productName);
        assertThat(result.get(0).getProduct().getType()).isEqualTo(type);
        assertThat(result.get(0).getProduct().getCount()).isEqualTo(count);

        assertThat(result.get(1).getStudent().getName()).isEqualTo(name);
        assertThat(result.get(1).getProduct().getName()).isEqualTo(productName2);
        assertThat(result.get(1).getProduct().getType()).isEqualTo(type2);
        assertThat(result.get(1).getProduct().getCount()).isEqualTo(count2);
    }
}
