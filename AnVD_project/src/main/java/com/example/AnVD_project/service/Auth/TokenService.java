package com.example.AnVD_project.service.Auth;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface TokenService {

    /**
     *
     * @param loginType
     * @param code
     * @return
     */
    Map<String, Object> authenticateAndFetchProfile(String loginType, String code);

    String generateUrl(HttpServletRequest request, String registrationId);
}
