package com.sssaang.api.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sssaang.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    public Long id;
    public String name;
    private String username;

    @JsonIgnore public String email;
    @JsonIgnore private String password;
    private GrantedAuthority authority;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }


    public CustomUserDetails(Long id, String name, String username, String email, String password,
                             GrantedAuthority authority) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public static CustomUserDetails create(User user) {
        GrantedAuthority authority = new  new SimpleGrantedAuthority(user.name);
        return new CustomUserDetails(user.id, user.name, user.username, user.email, user.password, authority);
    }
}
