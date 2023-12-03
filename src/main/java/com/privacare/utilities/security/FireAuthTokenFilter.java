package com.privacare.utilities.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class FireAuthTokenFilter extends OncePerRequestFilter {

    private final FirebaseAuth firebaseAuth;

    public FireAuthTokenFilter() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String idToken = extractTokenFromHeader(request.getHeader("Authorization"));

        if (idToken != null) {
            FirebaseToken firebaseToken = verifyFirebaseIdToken(idToken);
            String uid = firebaseToken.getUid();
            Boolean isAdmin = isAdminUser(firebaseToken);
            List<GrantedAuthority> authorities = null;
            if (isAdmin)
                authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

            FireAuthToken authToken = new FireAuthToken(uid, isAdmin, authorities);
            setAuthContext(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private FirebaseToken verifyFirebaseIdToken(String idToken) {
        try {
            return this.firebaseAuth.verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Boolean isAdminUser(FirebaseToken firebaseToken) {
        if (firebaseToken.getClaims().get("isAdmin") != null)
            return (Boolean) firebaseToken.getClaims().get("isAdmin");

        return false;
    }

    private void setAuthContext(FireAuthToken fireAuthToken) {
        SecurityContextHolder.getContext().setAuthentication(fireAuthToken);
    }

    private String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
