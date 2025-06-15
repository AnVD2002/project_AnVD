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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(secured)")
    public void checkPermission(Secured secured) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Access denied: User not authenticated");
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean hasRole = Arrays.stream(secured.roles())
                .map(roleEnum -> "ROLE_" + roleEnum.name())
                .anyMatch(requiredRole ->
                        authorities.stream().anyMatch(granted -> granted.getAuthority().equals(requiredRole))
                );

        if (!hasRole) {
            throw new AccessDeniedException("Access denied: User does not have the required role");
        }
    }
}

