package com.revature.PureDataBase2.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revature.PureDataBase2.DTO.responses.Principal;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final Logger logger = LoggerFactory.getLogger(JWTService.class);

    public String generateToken(Principal userPrincipal) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userPrincipal.getId());
        claims.put("role", userPrincipal.getRole());
        logger.debug("creating jwt token for user " + userPrincipal.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Set expiration time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, Principal userPrincipal) {
        String tokenUsername = extractUsername(userPrincipal.getToken());
        return (tokenUsername.equals(userPrincipal.getUsername()));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String extractUserId(String token) {
        return (String) extractAllClaims(token).get("id");
    }

    public String extractUserRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

}
