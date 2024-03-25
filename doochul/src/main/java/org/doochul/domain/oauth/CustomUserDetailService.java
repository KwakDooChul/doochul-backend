package org.doochul.domain.oauth;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String keyCode) throws UsernameNotFoundException {
        return userRepository.findBySocialInfoKeyCode(keyCode)
                .orElseThrow(() -> new UsernameNotFoundException("오류"));
    }
}
