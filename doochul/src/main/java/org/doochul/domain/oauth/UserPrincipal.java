package org.doochul.domain.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.doochul.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(final User user) {
        this.user = user;
    }

    @Override   // 사용자의 권한 목록 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 식별 정보에 따라서 권한을 동적으로 할당할 수 있음
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getIdentity().name()));
        return authorities;
    }

    public Long getSocialId() {
        return user.getSocialId();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
