package org.doochul.domain.user;

import java.util.Optional;
import org.doochul.common.exception.BackEndApplicationException;
import org.doochul.common.exception.ErrorCodes;
import org.doochul.domain.oauth.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    default User getById(final Long id) {
        return findById(id).orElseThrow(
                () -> new BackEndApplicationException(ErrorCodes.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    Optional<User> findBySocialIdAndSocialType(final Long socialId, final SocialType socialType);
}
