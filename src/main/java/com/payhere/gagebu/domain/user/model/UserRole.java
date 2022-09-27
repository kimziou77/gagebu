package com.payhere.gagebu.domain.user.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    LOGIN("ROLE_LOGIN"),
    ADMIN("ROLE_ADMIN");

    private final String grantedAuthority;

    public String getGrantedAuthority() {
        return grantedAuthority;
    }
}
