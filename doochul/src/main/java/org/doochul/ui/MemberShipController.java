package org.doochul.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.doochul.application.MemberShipService;
import org.doochul.common.resolver.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memberShip")
public class MemberShipController {

    private final MemberShipService memberShipService;

    @PostMapping("/apply/{productId}")
    public ResponseEntity<Void> apply(@AuthenticationPrincipal final Long userId, @PathVariable final Long productId) {
        Long memberShipId = memberShipService.save(userId, productId);
        return ResponseEntity.created(URI.create("/memberShips" + memberShipId)).build();
    }
}
