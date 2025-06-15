package com.example.AnVD_project.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String name;
    private String email;
    private String numberPhone;
    private Long roleId;
}
