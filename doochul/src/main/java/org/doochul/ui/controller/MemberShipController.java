package org.doochul.ui.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.application.service.MemberShipService;
import org.doochul.domain.MemberShip;
import org.doochul.ui.controller.dto.MemberShipDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memberShip")
public class MemberShipController {

    private final MemberShipService memberShipService;

    @GetMapping("/{userId}")
    private ResponseEntity<List<MemberShipDto>> findMemberShipsById(@PathVariable Long userId) {
        List<MemberShipDto> response = memberShipService.findMemberShipsById(userId);
        return ResponseEntity.ok(response);
    }
}
