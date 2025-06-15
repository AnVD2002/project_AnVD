package com.example.AnVD_project.service.Auth;

import com.example.AnVD_project.dto.request.user.RegisterRequest;
import com.example.AnVD_project.entity.Role;
import com.example.AnVD_project.entity.User;
import com.example.AnVD_project.enums.RoleEnum;
import com.example.AnVD_project.repository.RoleRepository;
import com.example.AnVD_project.repository.UserRepository;
import com.example.AnVD_project.until.Auth.UserInfoOauth2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RegisterServiceImpl implements RegisterService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserInfoOauth2 userInfoOauth2Constant;

    @Override
    public ResponseEntity<?> registerAccount(RegisterRequest request) {
        Map<String, Object> infoData = userInfoOauth2Constant.getUserInfo(request.getToken());

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

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
