package com.example.AnVD_project.DTO.Response;

import java.time.Instant;

public class LoginResponse {
    private String username;
    private Instant expireTime;
    private String accessToken;
    private String refreshToken;
}
