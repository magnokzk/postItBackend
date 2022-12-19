package com.example.postitback.services;

import com.example.postitback.entities.User;
import com.example.postitback.pojo.UserAuthRequest;
import com.example.postitback.repositories.AuthRepository;
import com.example.postitback.repositories.UserRepository;
import com.example.postitback.utils.CryptoManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    private final String secretKey = "ov4uDub1ZUpkHMWCX3B18T7dIJK6bS/c6YKgYJBI9IekQDp1nE7OUOf3n7/nvbsVO98Zq/47DJbgZZ8NnWUqBA==";

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthRepository authRepository;

    public UserAuthRequest atuhenticateUser(User user) throws Exception {
        User foundUser = authRepository.findUserByUsernameAndPassword(user.getUsername(), CryptoManager.encrypt(user.getPassword()));
        if(foundUser == null){
            throw new Exception("User.not.found");
        }

        String token =  Jwts.builder()
                .claim("userId", foundUser.getId())
                .claim("username", foundUser.getUsername())
                .claim("email", foundUser.getEmail())
                .setSubject("user")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        UserAuthRequest returnData = new UserAuthRequest();
        returnData.setId(foundUser.getId());
        returnData.setUsername(foundUser.getUsername());
        returnData.setEmail(foundUser.getEmail());
        returnData.setToken(token);

        return returnData;
    }
}
