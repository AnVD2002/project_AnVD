package com.example.AnVD_project.service.User;

import com.example.AnVD_project.DTO.User.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    /**
     *
     * @param request
     * @return
     */
    ResponseEntity<?> registerAccount(RegisterRequestDTO request);

    /**
     *
     * @param loginType
     * @param code
     * @return
     */
    Map<String, Object> authenticateAndFetchProfile(String loginType, String code);

    String generateUrl(HttpServletRequest request, String registrationId);
}
