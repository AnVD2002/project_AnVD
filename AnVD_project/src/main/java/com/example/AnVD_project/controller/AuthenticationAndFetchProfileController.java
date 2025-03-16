package com.example.AnVD_project.controller;

import com.example.AnVD_project.service.AuthenticateAndFetchProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class AuthenticationAndFetchProfileController {
    private final AuthenticateAndFetchProfileService authenticateAndFetchProfileService;
    @GetMapping("/auth-and-fetch-profile")
    public Map<String,Object> authenticateAndFetchProfile(@RequestParam String LoginType, @RequestParam String code) {
        return authenticateAndFetchProfileService.authenticateAndFetchProfile(LoginType, code);
    }

}
