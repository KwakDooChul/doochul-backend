package org.doochul.domain.oauth;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String socialId) throws UsernameNotFoundException {
        User principal = userRepository.findBySocialId(Long.parseLong(socialId))
                .orElseThrow(() -> new UsernameNotFoundException("오류"));
        return new UserPrincipal(principal);
    }
}
