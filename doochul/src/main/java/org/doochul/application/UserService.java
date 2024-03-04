package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.auth.PrincipalDetail;
import org.doochul.domain.user.Gender;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입 로직
     */
    @Transactional
    public Long save(User user) {
        String hashPw = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPw);
        return userRepository.save(user).getId();
    }

    /**
     * 회원 수정 로직
     */
    @Transactional
    public Long update(User user) {
        User userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + user.getId()));
        userEntity.update(user.getUsername(),bCryptPasswordEncoder.encode(user.getPassword()),user.getGender(),user.getIdentity());
        return userEntity.getId();
    }

    /**
     * 회원 중복 조회
     */
    @Transactional
    public boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

}
