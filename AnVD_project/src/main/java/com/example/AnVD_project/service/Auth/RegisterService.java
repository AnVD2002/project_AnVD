package com.example.AnVD_project.service.Auth;

import com.example.AnVD_project.dto.request.user.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface RegisterService {

    /**
     *
     * @param request
     * @return
     */
    ResponseEntity<?> registerAccount(RegisterRequest request);
}
