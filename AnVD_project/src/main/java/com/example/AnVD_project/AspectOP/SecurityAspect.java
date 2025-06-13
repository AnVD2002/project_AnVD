package com.example.AnVD_project.AspectOP;

import com.example.AnVD_project.enums.RoleEnum;
import com.example.AnVD_project.enums.Secured;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {
    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(secured)")
    public void checkPermission(Secured secured) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Access denied: User not authenticated");
        }

        String role = request.getHeader("X-Role");
        boolean hasRole = false;
        for (RoleEnum roleValid : secured.roles()) {
            if (StringUtils.equals(role, roleValid.name())) {
                hasRole = true;
                break;
            }
        }
        if (!hasRole) {
            throw new AccessDeniedException("Access denied: User dose not have required role");
        }
    }
}
