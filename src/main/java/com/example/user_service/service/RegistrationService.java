package com.example.user_service.service;

import com.example.user_service.errorhandling.BadRequestException;
import com.example.user_service.model.entity.User;
import com.example.user_service.model.request.UserRequest;
import com.example.user_service.repository.UsersRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void execute(UserRequest request){
        if(usersRepository.existsByEmail(request.getEmail())){
            log.error("Email : {} telah terdaftar",request.getEmail());
            throw new BadRequestException("Email yang anda masukan telah Digunakan");
        } else if (usersRepository.existsByUsername(request.getUsername())) {
            log.error("Username : {} telah terdaftar",request.getEmail());
            throw new BadRequestException("Username yang anda masukan telah Digunakan");
        }
        var now= new Date();
        var encrypt = bCryptPasswordEncoder.encode(request.getPassword());
        var user= new User()
                .setName(request.getName())
                .setUsername(request.getUsername())
                .setEmail(request.getEmail())
                .setCreatedAt(now)
                .setPassword(encrypt)
                .setRole(request.getRole());
        usersRepository.save(user);
    }

}
