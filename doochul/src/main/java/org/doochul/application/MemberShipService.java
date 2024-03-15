package org.doochul.application;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberShipService {

    private final MemberShipRepository memberShipRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;

    public Long save(final Long userId, final Long productId) {
        final Product product = productRepository.findById(productId).orElseThrow();
        final User user = userRepository.findById(userId).orElseThrow();

        final String key = Long.toString(userId);
        if (redisService.setNX(key, "apply", Duration.ofSeconds(5))) {
            final Long id = memberShipRepository.save(MemberShip.of(user, product, product.getCount())).getId();
            redisService.delete(key);
            return id;
        }
        throw new IllegalArgumentException();
    }
}
