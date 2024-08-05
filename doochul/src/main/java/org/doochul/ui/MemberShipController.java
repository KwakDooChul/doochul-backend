package org.doochul.ui;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.application.MemberShipService;
import org.doochul.common.resolver.AuthenticationPrincipal;
import org.doochul.ui.dto.MemberShipReadResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        return ResponseEntity.created(URI.create("/memberShips/" + memberShipId)).build();
    }

    @GetMapping("/memberShip/{productId}")
    public ResponseEntity<MemberShipReadResponse> readMemberShip(
            @AuthenticationPrincipal final Long userId,
            @PathVariable final Long productId
    ) {
        final MemberShipReadResponse readResponse = memberShipService.findMemberShip(userId, productId);
        return ResponseEntity.ok(readResponse);
    }

    @GetMapping("/memberShips")
    public ResponseEntity<List<MemberShipReadResponse>> readMemberShips(@AuthenticationPrincipal final Long userId) {
        final List<MemberShipReadResponse> readResponses = memberShipService.findMemberShips(userId);
        return ResponseEntity.ok(readResponses);
    }

    @DeleteMapping("/memberShip/{memberShipId}")
    public ResponseEntity<Void> deleteMemberShip(@PathVariable final Long memberShipId) {
        memberShipService.deleteMemberShip(memberShipId);
        return ResponseEntity.noContent().build();
    }
}
