package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.UserRepository;
import org.doochul.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long 회원가입(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);
        userRepository.save(user);
        return user.getId();
    }

}
