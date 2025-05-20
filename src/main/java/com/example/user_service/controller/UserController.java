package com.example.user_service.controller;

import com.example.user_service.model.response.LoginResponse;
import com.example.user_service.model.request.LoginRequest;
import com.example.user_service.model.request.UserRequest;
import com.example.user_service.service.LoginService;
import com.example.user_service.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final RegistrationService registrationService;
    private final LoginService login;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserRequest request){
         registrationService.execute(request);
         return ResponseEntity.status(HttpStatus.CREATED).body("Registrasi Berhasil");
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req){
        return login.execute(req);
    }

}


