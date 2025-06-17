package com.example.AnVD_project.service.User;

import com.example.AnVD_project.dto.response.user.UserResponseDTO;
import com.example.AnVD_project.entity.User;
import com.example.AnVD_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public Optional<User> loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> users = userRepository.findAllUsers();
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("No users found");
        }
        return users;
    }
}
