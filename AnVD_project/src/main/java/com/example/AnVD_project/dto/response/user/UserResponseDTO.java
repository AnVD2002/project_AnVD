package com.example.AnVD_project.dto.response.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private long id;
    private String username;
    private String password;
    private String numberPhone;
    private String email;
}
