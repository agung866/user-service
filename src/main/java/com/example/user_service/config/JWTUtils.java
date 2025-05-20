package com.example.user_service.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;


public class JWTUtils {
    private static JWTVerifier verifier ;

    public static String generatedToken(String username, String role,String secretKey){
        return JWT.create()
                .withIssuer("user-service")
                .withSubject(username)
                .withClaim("role",role)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(Algorithm.HMAC256(secretKey));
    }
    public static DecodedJWT verifyToken(String token) {
        return verifier.verify(token);
    }

    public String getRoleFromToken(String token) {
        return verifyToken(token).getClaim("role").asString();
    }

    public String getSubjectFromToken(String token) {
        return verifyToken(token).getSubject();
    }
}
