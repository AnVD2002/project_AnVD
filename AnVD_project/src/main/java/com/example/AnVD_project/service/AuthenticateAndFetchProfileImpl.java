package com.example.AnVD_project.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class AuthenticateAndFetchProfileImpl implements AuthenticateAndFetchProfileService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final WebClient webClient;



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
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
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
}
