package org.doochul.ui.dto;

import java.util.List;
import org.doochul.domain.membership.MemberShip;

public record MemberShipReadResponse(
        String userName,
        String teacherName,
        String productName,
        String productType,
        Integer count
) {
    public static MemberShipReadResponse from(final MemberShip memberShip) {
        return new MemberShipReadResponse(memberShip.getStudent().getName(), memberShip.getTeacher().getName(),
                memberShip.getProduct().getName(), memberShip.getProduct().getType().name(),
                memberShip.getRemainingCount());
    }

    public static List<MemberShipReadResponse> from(final List<MemberShip> memberShip) {
        return memberShip.stream()
                .map(MemberShipReadResponse::from)
                .toList();
    }
}
