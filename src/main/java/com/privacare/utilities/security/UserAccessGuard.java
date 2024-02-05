package com.privacare.utilities.security;

import com.privacare.utilities.exception.custom.UnauthorizedAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class UserAccessGuard {

    public static void checkUserAccess(String authId) {
        FireAuthToken authToken = (FireAuthToken) SecurityContextHolder.getContext().getAuthentication();
        if (!authToken.getUid().equals(authId) && !authToken.isAdmin()) {
            throw new UnauthorizedAccess(authToken.getUid() + " tries to access " + authId);
        }
    }
}
