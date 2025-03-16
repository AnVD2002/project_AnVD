package com.example.AnVD_project.DTO.User;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    String username;
    String password;
    String passwordConfirm;
    String token;
}
