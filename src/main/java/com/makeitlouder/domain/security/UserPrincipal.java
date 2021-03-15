package com.makeitlouder.domain.security;

import com.makeitlouder.domain.User;
import com.makeitlouder.domain.security.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = -8004361076660274304L;

    private User user;
    private UUID id;

    public UserPrincipal(User user) {
        this.user = user;
        this.id = user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> role.getAuthorities())
                .flatMap(Set::stream)
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toSet());

        user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .forEach(role -> authorities.add(role));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.isVerified();
    }
}
