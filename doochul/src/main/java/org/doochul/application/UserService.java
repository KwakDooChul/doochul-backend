package org.doochul.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doochul.domain.user.Identity;
import org.doochul.domain.user.SocialInfo;
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
    public String createUser(String keyCode, String nickname) {
        SocialInfo socialInfo = SocialInfo.of(keyCode, nickname);
        User user = User.of(Identity.GENERAL, socialInfo);
        userRepository.save(user);
        return user.getSocialInfo().getKeyCode();
    }
}
