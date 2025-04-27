package com.example.AnVD_project.controller;

import com.example.AnVD_project.DTO.Request.User.RegisterRequestDTO;
import com.example.AnVD_project.service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/generate-url")
    public String generateAuthorizationUrl(HttpServletRequest request,
                                           @RequestParam String registrationId) {
        return userService.generateUrl(request, registrationId);
    }

    @GetMapping("/auth-and-fetch-profile")
    public Map<String,Object> authenticateAndFetchProfile(@RequestParam String LoginType, @RequestParam String code) {
        return userService.authenticateAndFetchProfile(LoginType, code);
    }

    @PostMapping("/register-account")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequestDTO request){
        return userService.registerAccount(request);
    }
}
