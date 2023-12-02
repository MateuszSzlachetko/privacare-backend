package com.privacare.utilities.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

@AllArgsConstructor
public class FireAuthTokenResolver implements BearerTokenResolver {

    private final FirebaseAuth firebaseAuth;

    @Override
    public String resolve(HttpServletRequest request) {
        FirebaseToken token;
        try {
            token = this.firebaseAuth.verifyIdToken(request.getHeader("Authorization"));
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        return token.toString();
    }
}
