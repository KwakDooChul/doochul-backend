package org.doochul.auth;

import lombok.Data;
import org.doochul.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetail implements UserDetails {

    private User user;
    private Map<String, Object> attributes;

    // 일반 사용자
    public PrincipalDetail(User user) {
        this.user = user;
    }

    // auth 사용자
    public PrincipalDetail(User user, Map<String,Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }


    // 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> String.valueOf(user.getIdentity()));
        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
