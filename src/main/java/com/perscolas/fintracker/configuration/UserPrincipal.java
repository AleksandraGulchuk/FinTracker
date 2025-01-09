package com.perscolas.fintracker.configuration;

import com.perscolas.fintracker.model.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom implementation of the UserDetails interface, representing the authenticated user.
 * - Provides user authorities based on roles associated with the user.
 * - Implements methods for password, username, and account status checks (e.g., expiration, lock status).
 */
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final UserAccount user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getUserAccess().getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getUserAccess().isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getUserAccess().isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getUserAccess().isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getUserAccess().isEnabled();
    }

}
