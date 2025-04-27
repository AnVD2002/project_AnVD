package com.example.AnVD_project.service.User;

import com.example.AnVD_project.DTO.Request.User.RegisterRequestDTO;
import com.example.AnVD_project.Entity.Role;
import com.example.AnVD_project.Entity.User;
import com.example.AnVD_project.enums.RoleEnum;
import com.example.AnVD_project.repository.RoleRepository;
import com.example.AnVD_project.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final WebClient webClient;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;


    @Override
    public ResponseEntity<?> registerAccount(RegisterRequestDTO request) {
        Map<String, Object> infoData = getUserInfo(request.getToken());

        if (infoData == null || infoData.containsKey("error")) {
            return ResponseEntity.badRequest().body(infoData);
        }

        if (!Objects.equals(request.getPassword(), request.getPasswordConfirm())) {
            return new ResponseEntity<>("password not mapping", HttpStatus.BAD_REQUEST);
        }

        String email = infoData.get("email").toString();

        Optional<User> userOpt = userRepository.FindByEmail(email);


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

    @Override
    public Map<String, Object> authenticateAndFetchProfile(String loginType, String code) {
        String accessToken = null;
        System.out.println("clientID: " + (clientId));
        System.out.println("clientSecret: " + (clientSecret));
        System.out.println("redirectUri: " + (redirectUri));
        switch (loginType.toLowerCase()) {
            case "google":

                MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
                body.add("code", code);
                body.add("client_id", clientId);
                body.add("client_secret", clientSecret);
                body.add("redirect_uri", redirectUri);
                body.add("grant_type", "authorization_code");

                System.out.println("redirectUri: " + (body));

                Map<String, Object> tokenResponse = webClient.post()
                        .uri("/token")
                        .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                        })
                        .block();
                System.out.println("token: " + (tokenResponse));
                if (tokenResponse != null) {
                    accessToken = (String) tokenResponse.get("access_token");
                }
                break;

            default:
                throw new UnsupportedOperationException("Login type " + loginType + " is not supported.");
        }
        assert accessToken != null;
        return Map.of(
                "accessToken", accessToken,
                "loginType", loginType
        );
    }

    @Override
    public String generateUrl(HttpServletRequest request, String registrationId) {
        OAuth2AuthorizationRequest authorizationRequest = authorizationRequestResolver.resolve(request, registrationId);

        if (authorizationRequest == null) {
            throw new IllegalArgumentException("Không tìm thấy nhà cung cấp OAuth2: " + null);
        }
        return authorizationRequest.getAuthorizationRequestUri();
    }

    /*
        get user Info
     */
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
