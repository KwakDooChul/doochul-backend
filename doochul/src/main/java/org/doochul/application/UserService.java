package org.doochul.application;

import org.doochul.domain.User;
import org.doochul.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);

        userRepository.save(user);
    }

}
