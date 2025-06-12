package com.example.AnVD_project.service.Auth;

import com.example.AnVD_project.DTO.Request.User.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface RegisterService {

    /**
     *
     * @param request
     * @return
     */
    ResponseEntity<?> registerAccount(RegisterRequestDTO request);
}
