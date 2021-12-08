package com.sssaang.api.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sssaang.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    public Long id;
    public String name;
    private String username;

    @JsonIgnore public String email;
    @JsonIgnore private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String name, String username, String email, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(User user) {
        GrantedAuthority authority = new  new SimpleGrantedAuthority(user.name);
        return new CustomUserDetails(user.id, user.name, user.username, user.email, user.password, authority);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return true;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomUserDetails that = (CustomUserDetails) o;
        return Objects.equals(id, that.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }
}
