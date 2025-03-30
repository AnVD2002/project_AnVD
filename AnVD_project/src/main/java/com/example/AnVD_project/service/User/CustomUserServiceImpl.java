package com.example.AnVD_project.service.User;

import com.example.AnVD_project.Entity.CustomUserDetail;
import com.example.AnVD_project.Entity.User;
import com.example.AnVD_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserServiceImpl implements CustomUserService {
    private final UserRepository userRepository;

    public CustomUserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.FindByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(email);
        }
        else {
            return new CustomUserDetail(user.get());
        }
    }
}
