package com.example.AnVD_project.service.Auth;

import com.example.AnVD_project.DTO.Request.User.RegisterRequest;
import com.example.AnVD_project.Entity.Role;
import com.example.AnVD_project.Entity.User;
import com.example.AnVD_project.enums.RoleEnum;
import com.example.AnVD_project.repository.RoleRepository;
import com.example.AnVD_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service

@RequiredArgsConstructor

public class RegisterServiceImpl implements RegisterService {

    private final WebClient webClient;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    @Override
    public ResponseEntity<?> registerAccount(RegisterRequest request) {
        Map<String, Object> infoData = getUserInfo(request.getToken());

        if (infoData == null || infoData.containsKey("error")) {
            return ResponseEntity.badRequest().body(infoData);
        }

        if (!Objects.equals(request.getPassword(), request.getPasswordConfirm())) {
            return new ResponseEntity<>("password not mapping", HttpStatus.BAD_REQUEST);
        }

        String email = infoData.get("email").toString();

        Optional<User> userOpt = userRepository.findByEmail(email);


        userOpt.ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User already exists");
        });


        Optional<Role> roleOpt = roleRepository.findRoleByRoleName(RoleEnum.USER);

        Role role = roleOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(request.getPassword());
        newUser.setName(request.getUsername());
        newUser.setRole(role);
        userRepository.save(newUser);

        return ResponseEntity.ok().body(infoData);
    }


    public Map<String, Object> getUserInfo(String accessToken) {
        String googleUserInfoEndpoint = "https://www.googleapis.com/oauth2/v2/userinfo";

        return webClient.get()
                .uri(googleUserInfoEndpoint)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }
}
