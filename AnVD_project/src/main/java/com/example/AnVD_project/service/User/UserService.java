package com.example.AnVD_project.service.User;

import com.example.AnVD_project.dto.response.user.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
}
