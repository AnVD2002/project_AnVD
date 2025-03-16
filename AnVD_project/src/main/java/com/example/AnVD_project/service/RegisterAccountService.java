package com.example.AnVD_project.service;

import com.example.AnVD_project.DTO.User.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface RegisterAccountService {
    public ResponseEntity<?> registerAccount(RegisterRequestDTO request);
}
