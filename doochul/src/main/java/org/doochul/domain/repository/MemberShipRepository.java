package org.doochul.domain.repository;

import java.util.List;
import org.doochul.domain.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {

    List<MemberShip> findByUserId(Long userId);
}
