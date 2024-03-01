package org.doochul.ui.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.doochul.domain.MemberShip;
import org.doochul.domain.MemberShipType;

public record MemberShipDto(
        Long id,
        MemberShipType type,
        User user,
        int count,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static MemberShipDto from(MemberShip memberShip) {
        return new MemberShipDto(memberShip.getId(), memberShip.getType(), memberShip.getUser(),
                memberShip.getCount(), memberShip.getCreatedAt(), memberShip.getUpdatedAt());
    }

    public static List<MemberShipDto> fromList(List<MemberShip> memberShips) {
        return memberShips.stream()
                .map(MemberShipDto::from)
                .toList();
    }
}
