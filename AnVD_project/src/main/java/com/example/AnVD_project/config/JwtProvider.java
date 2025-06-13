package com.example.AnVD_project.config;
import com.example.AnVD_project.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {
    private static final String SECRET_KEY = "e82c73692e6fa99b1770cfd6605bfc5b9ec3a12b362d9de5459a2612191497c4";
    private static final long JWT_EXPIRATION = 3600000L; // 1 giờ
    private static final long REFRESH_TOKEN_EXPIRATION = 2592000000L;

    /*
        * Extracts the username from the JWT token.
     */
    public String ExtractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /*
        * Extracts the user details from the JWT token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
        * Checks if the token is expired.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /*
        * Extracts all claims from the JWT token.
     */
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
        * Extracts the expiration date from the JWT token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
        * Validates the JWT token against the user details.
     */
    public boolean isTokenValid(String token, User user) {
        final String username = ExtractUserName(token);
        return (username.equals(user.getEmail())) && !isTokenExpired(token);
    }
    /*

     */
    public String generateAccessToken(User user) {
        return generateToken(user, JWT_EXPIRATION);
    }
    /*
        * Generates a refresh token for the user.
     */
    public String generateRefreshToken(User user) {
        return generateToken(user, REFRESH_TOKEN_EXPIRATION);
    }

    /*
        * Generates a JWT token for the user with a specified expiration time.
     */
    public String generateToken(User user, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }








    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

}
