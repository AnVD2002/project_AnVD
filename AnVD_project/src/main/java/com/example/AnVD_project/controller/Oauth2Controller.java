package com.example.AnVD_project.controller;

import com.example.AnVD_project.dto.request.user.RegisterRequest;
import com.example.AnVD_project.service.Auth.RegisterService;
import com.example.AnVD_project.service.Auth.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final TokenService tokenService;

    private final RegisterService registerService;

    @GetMapping("/generate-url")
    public String generateAuthorizationUrl(HttpServletRequest request,
                                           @RequestParam String registrationId) {
        return tokenService.generateUrl(request, registrationId);
    }

    @GetMapping("/auth-and-fetch-profile")
    public Map<String,Object> authenticateAndFetchProfile(@RequestParam String LoginType, @RequestParam String code) {
        return tokenService.authenticateAndFetchProfile(LoginType, code);
    }

    @PostMapping("/register-account")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequest request){
        return registerService.registerAccount(request);
    }

}
