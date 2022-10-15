package com.example.postitback.controllers;

import com.example.postitback.entities.User;
import com.example.postitback.repositories.AuthRepository;
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

    @PostMapping
    @CrossOrigin("http://localhost:3000")
    private ResponseEntity<?> save(@RequestBody User user){
        try{
            User foundUser = repository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
            if(foundUser == null){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<User>(foundUser, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
