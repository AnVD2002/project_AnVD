package com.example.AnVD_project.controller;

import com.example.AnVD_project.service.AuthenticateAndFetchProfileService;
import com.example.AnVD_project.service.Oauth2LoginUrlGeneratorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class AuthenticationAndFetchProfileController {
    private final AuthenticateAndFetchProfileService authenticateAndFetchProfileService;
    @GetMapping("/AuthAndFetchProfile")
    public Map<String,Object> authenticateAndFetchProfile(@RequestParam String LoginType, @RequestParam String code) {
        return authenticateAndFetchProfileService.authenticateAndFetchProfile(LoginType, code);
    }

}
