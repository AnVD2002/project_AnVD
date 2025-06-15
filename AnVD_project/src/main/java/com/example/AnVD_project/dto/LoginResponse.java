package com.example.AnVD_project.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    String access_token;
    String refresh_token;
    Long expire_time;
    String role;
}
