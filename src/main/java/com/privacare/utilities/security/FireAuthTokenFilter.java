package com.privacare.utilities.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            FirebaseToken firebaseToken;
            try {
                firebaseToken = this.firebaseAuth.verifyIdToken(idToken);
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e.getMessage());
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(firebaseToken.getUid(), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
