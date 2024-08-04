package org.doochul.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.ProductCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MemberShipServiceTest {

    @InjectMocks
    private MemberShipService memberShipService;

    @Mock
    private MemberShipRepository memberShipRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedisService redisService;

    private User user;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private Product product;

    @BeforeEach
    void setUp() {
        user = User.of(1L, 1L, "kakao", "테스트 유저 1");
        user2 = User.of(2L, 2L, "kakao", "테스트 유저 2");
        user3 = User.of(3L, 3L, "kakao", "테스트 유저 3");
        user4 = User.of(4L, 4L, "kakao", "테스트 유저 4");
        user5 = User.of(5L, 5L, "kakao", "테스트 유저 5");
        User teacher = User.of(6L, 6L, "kakao", "테스트 쌤 1");

        when(userRepository.getById(1L)).thenReturn(user);
        when(userRepository.getById(2L)).thenReturn(user2);
        when(userRepository.getById(3L)).thenReturn(user3);
        when(userRepository.getById(4L)).thenReturn(user4);
        when(userRepository.getById(5L)).thenReturn(user5);
        when(userRepository.getById(6L)).thenReturn(teacher);

        ProductCreateRequest request = new ProductCreateRequest("페이커", ProductType.LOL, 10);
        product = Product.of(teacher, request);

        when(productRepository.getById(1L)).thenReturn(product);

        MemberShip memberShip = new MemberShip(1L, user, product, 10);
        when(memberShipRepository.save(any())).thenReturn(memberShip);
    }

    @Test
    void save() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 1; i < 6; i++) {
            int userIdx = i;
            executorService.submit(() -> {
                try {
                    final String key = Long.toString(userIdx);
                    if (redisService.setNX(key, "apply", Duration.ofSeconds(5))) {
                        memberShipRepository.save(MemberShip.of(user, product, product.getCount()));
                        redisService.delete(key);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        //then
        then(memberShipRepository).should(times(1)).save(any());
    }
}
