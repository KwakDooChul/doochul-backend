package org.doochul.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.common.exception.BackEndApplicationException;
import org.doochul.common.exception.ErrorCodes;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.membership.MemberShipRepository;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductRepository;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.MemberShipReadResponse;
import org.springframework.http.HttpStatus;
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

    public Long createMemberShip(final Long userId, final Long productId) {
        final User user = userRepository.getById(userId);
        final Product product = productRepository.getById(productId);
//        final String key = Long.toString(userId);
//        if (redisService.setNX(key, "apply", Duration.ofSeconds(5))) {
//            final MemberShip memberShip = memberShipRepository.save(MemberShip.of(user, product, product.getCount()));
//            redisService.delete(key);
//            return memberShip.getId();
//        }
        final MemberShip memberShip = memberShipRepository.save(MemberShip.of(user, product, product.getCount()));
//        //TODO
//        throw new IllegalArgumentException();
        return memberShip.getId();
    }

    public MemberShipReadResponse findMemberShip(final Long userId, final Long productId) {
        final User user = userRepository.getById(userId);
        final Product product = productRepository.getById(productId);
        final MemberShip memberShip = memberShipRepository.findByStudentAndProduct(user, product).orElseThrow(()
                -> new BackEndApplicationException(ErrorCodes.MEMBERSHIP_NOT_FOUND, HttpStatus.NOT_FOUND));
        return MemberShipReadResponse.from(memberShip);
    }

    public List<MemberShipReadResponse> findMemberShips(final Long userId) {
        final User user = userRepository.getById(userId);
        final List<MemberShip> memberShips = memberShipRepository.findByStudent(user);
        return MemberShipReadResponse.from(memberShips);
    }

    public void deleteMemberShip(final Long memberShipId) {
        memberShipRepository.deleteById(memberShipId);
    }
}
