package com.example.AnVD_project.controller;


import com.example.AnVD_project.service.Oauth2LoginUrlGeneratorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class GenerateAuthorizationUrlController {
    private final Oauth2LoginUrlGeneratorService oauth2LoginUrlGeneratorService;
    @GetMapping("/generate-url")
    public String generateAuthorizationUrl(HttpServletRequest request,
                                           @RequestParam String registrationId) {
        return oauth2LoginUrlGeneratorService.generateUrl(request, registrationId);
    }
}
