package com.example.AnVD_project.service.Auth;

import com.example.AnVD_project.entity.User;
import com.example.AnVD_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void logout(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setRefreshToken(null);
            user.get().setExpireTime(null);
            userRepository.save(user.get());
        }
    }
}
