package com.example.AnVD_project.service;
import java.util.Map;

public interface AuthenticateAndFetchProfileService {
    public Map<String,Object> authenticateAndFetchProfile(String code, String loginType);
}
