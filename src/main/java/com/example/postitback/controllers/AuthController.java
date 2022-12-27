package com.example.postitback.controllers;

import com.example.postitback.entities.User;
import com.example.postitback.pojo.UserAuthRequest;
import com.example.postitback.repositories.AuthRepository;
import com.example.postitback.services.UserService;
import com.example.postitback.utils.CryptoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/controllers/auth")
public class AuthController {
    @Autowired
    AuthRepository repository;

    @Autowired
    UserService userService;

    @PostMapping
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<?> save(@RequestBody User user){
        try{
            UserAuthRequest returnUser = userService.authenticateUser(user);

            return new ResponseEntity<UserAuthRequest>(returnUser, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
    }
}
