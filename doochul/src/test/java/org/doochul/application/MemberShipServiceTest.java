package org.doochul.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.MemberShipReadResponse;
import org.doochul.ui.dto.ProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberShipServiceTest {

    @InjectMocks
    private MemberShipService memberShipService;

    @Mock
    private MemberShipRepository memberShipRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("멤버쉽을 저장한다.")
    @Test
    void createMemberShip() {
        //given
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";
        final User user = User.of(userId, socialId, socialType, name);

        final Long productId = 1L;
        final String productName = "안녕";
        final ProductType type = ProductType.LOL;
        final Integer count = 10;

        final ProductCreateRequest productCreateRequest = new ProductCreateRequest(productName, type, count);

        final Product product = Product.of(productId, user, productCreateRequest);

        final MemberShip memberShip = MemberShip.of(1L, user, product, 10);

        given(userRepository.getById(any())).willReturn(user);
        given(productRepository.getById(any())).willReturn(product);
        given(memberShipRepository.save(any())).willReturn(memberShip);

        //when
        final Long memberShipId = memberShipService.createMemberShip(userId, productId);

        //then
        assertThat(memberShipId).isEqualTo(1L);
    }

    @DisplayName("PT에 대한 멤버쉽을 조회한다.")
    @Test
    void findMemberShip() {
        // given
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";
        final User user = User.of(userId, socialId, socialType, name);

        final Long productId = 1L;
        final String productName = "안녕";
        final ProductType type = ProductType.LOL;
        final Integer count = 10;

        final ProductCreateRequest productCreateRequest = new ProductCreateRequest(productName, type, count);

        final Product product = Product.of(productId, user, productCreateRequest);

        final MemberShip memberShip = MemberShip.of(1L, user, product, 10);

        given(userRepository.getById(any())).willReturn(user);
        given(productRepository.getById(any())).willReturn(product);
        given(memberShipRepository.findByStudentAndProduct(any(), any())).willReturn(Optional.of(memberShip));

        // when
        final MemberShipReadResponse response = memberShipService.findMemberShip(userId, productId);

        // then
        assertThat(response.userName()).isEqualTo(name);
        assertThat(response.teacherName()).isEqualTo(name);
        assertThat(response.productName()).isEqualTo(productName);
        assertThat(response.productType()).isEqualTo(type.name());
        assertThat(response.count()).isEqualTo(count);
    }

    @DisplayName("학생이 구독한 멤버쉽을 전체 조회한다.")
    @Test
    void findMemberShips() {
        // given
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";
        final User user = User.of(userId, socialId, socialType, name);

        final Long productId = 1L;
        final String productName = "안녕";
        final ProductType type = ProductType.LOL;
        final Integer count = 10;

        final String productName2 = "안녕2";
        final ProductType type2 = ProductType.TFT;
        final Integer count2 = 20;

        final ProductCreateRequest productCreateRequest = new ProductCreateRequest(productName, type, count);
        final ProductCreateRequest productCreateRequest2 = new ProductCreateRequest(productName2, type2, count2);

        final Product product = Product.of(productId, user, productCreateRequest);
        final Product product2 = Product.of(productId, user, productCreateRequest2);

        final MemberShip memberShip = MemberShip.of(1L, user, product, product.getCount());
        final MemberShip memberShip2 = MemberShip.of(1L, user, product2, product2.getCount());

        given(userRepository.getById(any())).willReturn(user);
        given(memberShipRepository.findByStudent(any())).willReturn((List.of(memberShip, memberShip2)));

        // when
        final List<MemberShipReadResponse> response = memberShipService.findMemberShips(userId);

        //then
        assertThat(response.get(0).userName()).isEqualTo(name);
        assertThat(response.get(0).teacherName()).isEqualTo(name);
        assertThat(response.get(0).productName()).isEqualTo(productName);
        assertThat(response.get(0).productType()).isEqualTo(type.name());
        assertThat(response.get(0).count()).isEqualTo(count);

        assertThat(response.get(1).userName()).isEqualTo(name);
        assertThat(response.get(1).teacherName()).isEqualTo(name);
        assertThat(response.get(1).productName()).isEqualTo(productName2);
        assertThat(response.get(1).productType()).isEqualTo(type2.name());
        assertThat(response.get(1).count()).isEqualTo(count2);
    }
}
