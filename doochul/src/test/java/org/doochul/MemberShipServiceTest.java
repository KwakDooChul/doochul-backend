package org.doochul;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.doochul.application.RedisService;
import org.doochul.domain.membership.MemberShip;
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
public class MemberShipServiceTest {

    @MockBean
    private MemberShipRepository memberShipRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Test
    void memberShip_save() throws InterruptedException {
        // Given
        User user = User.of("123", "1234");
        User teacher = User.of("1234", "123");

        userRepository.save(user);
        userRepository.save(teacher);

        Product product = Product.of(teacher, new ProductRegisterRequest("페이커", ProductType.LOL, 10));
        productRepository.save(product);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        given(memberShipRepository.save(any())).willReturn(new MemberShip(1L, user, product, 10));

        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    final String key = Long.toString(user.getId());
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

        then(memberShipRepository).should(times(1)).save(any());
    }
}
