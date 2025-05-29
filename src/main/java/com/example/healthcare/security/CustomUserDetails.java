package com.example.healthcare.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {

    @Getter
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    @Getter
    private String userRole;

    private CustomUserDetails(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.authorities = builder.authorities;
        this.userRole = builder.userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public static class Builder {
        private Long id;
        private String email;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private String userRole;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }
        public Builder userRole(String userRole) {
            this.userRole = userRole;
            return this;
        }

        public CustomUserDetails build() {
            return new CustomUserDetails(this);
        }
    }
}
