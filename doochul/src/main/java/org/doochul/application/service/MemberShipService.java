package org.doochul.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.domain.MemberShip;
import org.doochul.domain.repository.MemberShipRepository;
import org.doochul.ui.controller.dto.MemberShipDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberShipService {

    private final MemberShipRepository memberShipRepository;

    public List<MemberShipDto> findMemberShipsById(Long userId) {
        List<MemberShip> memberShips = memberShipRepository.findByUserId(userId);
        return MemberShipDto.fromList(memberShips);
    }
}
