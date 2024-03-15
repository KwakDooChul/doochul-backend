package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.MemberShipService;
import org.doochul.domain.membership.MemberShip;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public MemberShip apply(
            @AuthenticationPrincipal final Long userId,
            @PathVariable Long productId) {

        return memberShipService.save(userId, productId);
    }
}
