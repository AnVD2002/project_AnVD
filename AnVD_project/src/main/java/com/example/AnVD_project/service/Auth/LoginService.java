package com.example.AnVD_project.service.Auth;

import com.example.AnVD_project.DTO.LoginResponse;
import com.example.AnVD_project.DTO.Request.User.LoginRequest;
import com.example.AnVD_project.DTO.Request.User.Oauth2Request;
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
