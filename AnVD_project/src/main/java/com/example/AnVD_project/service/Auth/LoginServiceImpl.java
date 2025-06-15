package com.example.AnVD_project.service.Auth;

import com.example.AnVD_project.DTO.LoginResponse;
import com.example.AnVD_project.DTO.Request.User.LoginRequest;
import com.example.AnVD_project.DTO.Request.User.Oauth2Request;
import com.example.AnVD_project.Entity.User;
import com.example.AnVD_project.config.JwtProvider;
import com.example.AnVD_project.repository.UserRepository;
import com.example.AnVD_project.service.User.UserService;
import com.example.AnVD_project.until.Auth.UserInfoOauth2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserInfoOauth2 userInfoOauth2;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<LoginResponse> loginOauth2(Oauth2Request request) {

        try {
            Map<String, Object> userInfo = userInfoOauth2.getUserInfo(request.getAccessToken());

            if (CollectionUtils.isEmpty(userInfo)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid access token");
            }

            String mail = userInfo.get("email").toString();

            Optional<User> user = userService.loadUserByUsername(mail);

            LoginResponse loginResponse = processLoginUser(user);

            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "OAuth2 token is invalid or expired", ex);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid argument", ex);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", e);
        }
    }

    @Override
    public ResponseEntity<LoginResponse> loginDefault(LoginRequest request) {
        try {
            Optional<User> userCheckExist = userService.loadUserByUsername(request.getUsername());

            if (userCheckExist.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "account not found");
            }

            Optional<User> user = userRepository.findByUserNamePassword(request.getUsername(), request.getPassword());

            return ResponseEntity.ok(processLoginUser(user));
        }
        catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid argument", ex);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", e);
        }

    }


    public LoginResponse processLoginUser(Optional<User> user) {
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "account not found");
        }

        String accessToken = jwtProvider.generateAccessToken(user.get());

        String refreshToken = jwtProvider.generateRefreshToken(user.get());

        Long expireTime = System.currentTimeMillis() + JwtProvider.REFRESH_TOKEN_EXPIRATION;

        String role = user.get().getRole().getRoleName().toString();

        user.get().setRefreshToken(refreshToken);
        user.get().setExpireTime(expireTime);
        user.get().setAccessToken(accessToken);
        userRepository.save(user.get());

        return LoginResponse.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .expire_time(expireTime)
                .role(role)
                .build();

    }
}
