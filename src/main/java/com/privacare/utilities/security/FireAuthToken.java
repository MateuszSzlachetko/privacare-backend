package com.privacare.utilities.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class FireAuthToken extends UsernamePasswordAuthenticationToken {
    private final String uid;
    private final boolean isAdmin;

    public FireAuthToken(String uid, boolean isAdmin, Collection<? extends GrantedAuthority> authorities) {
        super(uid, null, authorities);
        this.uid = uid;
        this.isAdmin = isAdmin;
    }
}
