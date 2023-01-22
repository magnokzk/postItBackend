package com.example.postitback.controllers;

import com.example.postitback.entities.User;
import com.example.postitback.repositories.UserRepository;
import com.example.postitback.utils.CryptoManager;
import com.example.postitback.utils.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/controllers/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> getUserByToken(HttpServletRequest request){
        User user = JwtManager.getUserFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity<Object>(repository.findById(user.getId()), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        Optional<User> user = repository.findById(id);
        return new ResponseEntity<Object>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ResponseEntity<?> save(@RequestBody User user){
        try{
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(CryptoManager.encrypt(user.getPassword()));
            repository.save(newUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
