package org.doochul.domain.membership;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {
}
