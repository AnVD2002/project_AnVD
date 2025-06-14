package com.example.AnVD_project.config;

import com.example.AnVD_project.entity.User;
import com.example.AnVD_project.service.User.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtProvider jwtProvider;

    @Autowired
    private final UserServiceImpl userServiceImpl;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtProvider.ExtractUserName(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> user = userServiceImpl.loadUserByUsername(username);
            User userDetails = user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
            long now = System.currentTimeMillis();
            if(!StringUtils.equals(jwt, userDetails.getAccessToken())) {
                handleErrorResponse(response, "Unauthorized access attempt");
                return;
            }

            if (now >= userDetails.getExpireTime()) {
                handleErrorResponse(response, "Token expired");
                return;
            }

            if (jwtProvider.isTokenValid(jwt, userDetails)) {
                String role = userDetails.getRole().getRoleName().toString();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of(authority)
                        );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"code\": \"" + HttpStatus.UNAUTHORIZED.value() + "\", \"message\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
