package com.example.user_service.model.response;


import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private String token;
}

