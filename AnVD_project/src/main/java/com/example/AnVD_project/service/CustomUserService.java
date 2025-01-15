package com.example.AnVD_project.service;

import com.example.AnVD_project.entity.CustomUserDetail;

public interface CustomUserService {
    public CustomUserDetail loadUserByUsername(String email);
}
