package com.beam.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN("Admin"),ROLE_USER("User");

    private String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
