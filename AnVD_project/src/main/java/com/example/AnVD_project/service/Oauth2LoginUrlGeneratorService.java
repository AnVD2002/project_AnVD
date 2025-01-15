package com.example.AnVD_project.service;

import jakarta.servlet.http.HttpServletRequest;


public interface Oauth2LoginUrlGeneratorService {
    public String generateUrl(HttpServletRequest request, String registrationId);
}
