package org.doochul.application;

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

    public MemberShip save(Long userId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        return memberShipRepository.save(MemberShip.of(user, product, product.getCount()));
    }
}
