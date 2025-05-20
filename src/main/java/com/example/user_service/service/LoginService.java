package com.example.user_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.user_service.config.JWTUtils;
import com.example.user_service.errorhandling.BadRequestException;
import com.example.user_service.model.response.LoginResponse;
import com.example.user_service.model.request.LoginRequest;
import com.example.user_service.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final UsersRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secretKey}")
    private String secretKey;
    public LoginResponse execute(LoginRequest loginRequst) {
        var userData = userRepository.findByUsername(loginRequst.getUsername());
        if (userData == null) {
            log.error("Username : {} tidak  ada",loginRequst.getUsername());
            throw new BadRequestException("username yang anda masukan salah ");
        } else if (!bCryptPasswordEncoder.matches(loginRequst.getPassword(), userData.getPassword())) {
            log.error("password yang anda masukan salah");
            throw new BadRequestException("password yang anda masukan salah");
        }
        var token= JWTUtils.generatedToken(loginRequst.getUsername(),userData.getRole(),secretKey);
        var res = new LoginResponse();
        res.setUsername(loginRequst.getUsername());
        res.setToken(token);
        return res;

    }

}
