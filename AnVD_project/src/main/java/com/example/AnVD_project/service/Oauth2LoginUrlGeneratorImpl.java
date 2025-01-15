package com.example.AnVD_project.service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Oauth2LoginUrlGeneratorImpl implements Oauth2LoginUrlGeneratorService {
    private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;
    @Override
    public String generateUrl(HttpServletRequest request, String registrationId) {
        OAuth2AuthorizationRequest authorizationRequest = authorizationRequestResolver.resolve(request, registrationId);

        if (authorizationRequest == null) {
            throw new IllegalArgumentException("Không tìm thấy nhà cung cấp OAuth2: " + null);
        } 
        return authorizationRequest.getAuthorizationRequestUri();
    }
}
