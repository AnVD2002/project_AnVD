package com.example.AnVD_project.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserInfoResponse {
    private Long Id;
    private String Name;
    private String Email;
    private String Phone;

}
