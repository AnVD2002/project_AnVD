package com.example.AnVD_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final WebClient webClient;
    public Map<String, Object> getUserInfo(String accessToken) {
        String googleUserInfoEndpoint = "https://www.googleapis.com/oauth2/v2/userinfo";

        return webClient.get()
                .uri(googleUserInfoEndpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
