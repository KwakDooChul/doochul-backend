package org.doochul.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.user.Identity;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String createUser(final String socialId, final String nickname) {
        User user = User.of(Identity.GENERAL, socialId, nickname);
        userRepository.save(user);
        return user.getSocialId().toString();
    }

    @Transactional
    public Optional<User> findUserBySocialId(final Long socialId) {
        return userRepository.findBySocialId(socialId);
    }
}
