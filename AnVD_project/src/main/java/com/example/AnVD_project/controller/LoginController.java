package com.example.AnVD_project.controller;

import com.example.AnVD_project.dto.request.user.LoginRequest;
import com.example.AnVD_project.dto.request.user.Oauth2Request;
import com.example.AnVD_project.service.Auth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    private final LoginService loginService;

    @PostMapping("/default")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return loginService.loginDefault(loginRequest);
    }

    @PostMapping("/oauth2")
    public ResponseEntity<?> oauth2(@RequestBody Oauth2Request oauth2request) {
        return loginService.loginOauth2(oauth2request);
    }
}
