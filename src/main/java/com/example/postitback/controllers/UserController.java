package com.example.postitback.controllers;

import com.example.postitback.entities.User;
import com.example.postitback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/controllers/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @GetMapping
    ResponseEntity<?> listar(){
        return new ResponseEntity<Object>(repository.findAll(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody User user){
        try{
            repository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
