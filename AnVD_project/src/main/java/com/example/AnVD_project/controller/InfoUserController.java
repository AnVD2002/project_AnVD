package com.example.AnVD_project.controller;

import com.example.AnVD_project.service.Oauth2LoginUrlGeneratorService;
import com.example.AnVD_project.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class InfoUserController {
    private final UserInfoService userInfoService;
    @GetMapping("/generate-info")
    public Map<String, Object> generateInfoUser(@RequestParam String accessToken) {
        return userInfoService.getUserInfo(accessToken);
    }
}
