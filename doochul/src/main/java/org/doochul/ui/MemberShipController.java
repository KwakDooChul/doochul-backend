package org.doochul.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.doochul.application.MemberShipService;
import org.doochul.common.resolver.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberShipController {

    private final MemberShipService memberShipService;

    @PostMapping("/memberShip/{productId}")
    public ResponseEntity<Long> createMemberShip(@AuthenticationPrincipal final Long userId,
                                                 @PathVariable final Long productId) {
        final Long memberShipId = memberShipService.createMemberShip(userId, productId);
        return ResponseEntity.created(URI.create("/memberShips" + memberShipId)).build();
    }
}
