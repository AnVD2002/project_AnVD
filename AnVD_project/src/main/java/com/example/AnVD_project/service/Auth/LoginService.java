package com.example.AnVD_project.service.Auth;

import com.example.AnVD_project.dto.LoginResponse;
import com.example.AnVD_project.dto.request.user.LoginRequest;
import com.example.AnVD_project.dto.request.user.Oauth2Request;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    /**
     *
     * @param request
     * @return
     */
    ResponseEntity<LoginResponse> loginOauth2(Oauth2Request request);

    /**
     *
     * @param request
     * @return
     */
    ResponseEntity<LoginResponse> loginDefault(LoginRequest request);



}
