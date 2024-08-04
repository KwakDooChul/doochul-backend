package org.doochul.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductCreateRequest;
import org.doochul.ui.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    @DisplayName("product 저장 테스트")
    @Test
    void save() {
        // given
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";

        final User user = User.of(userId, socialId, socialType, name);
        final ProductCreateRequest productCreateRequest = new ProductCreateRequest("안녕", ProductType.LOL, 10);
        final Product product = Product.of(1L, user, productCreateRequest);

        given(userRepository.getById(1L)).willReturn(user);
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        final Long productId = productService.createProduct(userId, productCreateRequest);

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("product 조회 테스트")
    @Test
    void findProduct() {
        // given
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";

        final User user = User.of(userId, socialId, socialType, name);
        final ProductCreateRequest productCreateRequest = new ProductCreateRequest("안녕", ProductType.LOL, 10);
        final Product product = Product.of(1L, user, productCreateRequest);

        given(productRepository.getById(1L)).willReturn(product);

        // when
        final ProductResponse productResponse = productService.findProduct(1L);

        // then
        assertThat(productResponse.id()).isEqualTo(1L);
        assertThat(productResponse.name()).isEqualTo("안녕");
        assertThat(productResponse.type()).isEqualTo(ProductType.LOL);
        assertThat(productResponse.teacher()).isEqualTo("카카오 유저 1");
        assertThat(productResponse.count()).isEqualTo(10);
    }

    @DisplayName("product 전체 조회 테스트")
    @Test
    void findProducts() {
        // given
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";

        final User user = User.of(userId, socialId, socialType, name);
        final ProductCreateRequest productCreateRequest = new ProductCreateRequest("안녕", ProductType.LOL, 10);
        final Product product = Product.of(1L, user, productCreateRequest);

        given(productRepository.findAll()).willReturn(List.of(product));

        // when
        final List<ProductResponse> productResponses = productService.findProducts();

        // then
        assertThat(productResponses).hasSize(1);
        assertThat(productResponses.get(0).id()).isEqualTo(1L);
    }

    @DisplayName("product 삭제 테스트")
    @Test
    void deleteProduct() {
        // when
        productService.deleteProduct(1L);

        // then
        verify(productRepository).deleteById(1L);
    }
}
