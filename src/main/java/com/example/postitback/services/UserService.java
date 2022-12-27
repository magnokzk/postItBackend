package com.example.postitback.services;

import com.example.postitback.entities.User;
import com.example.postitback.pojo.UserAuthRequest;
import com.example.postitback.repositories.AuthRepository;
import com.example.postitback.repositories.UserRepository;
import com.example.postitback.utils.CryptoManager;
import com.example.postitback.utils.JwtManager;
import io.jsonwebtoken.Claims;
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


    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthRepository authRepository;

    public UserAuthRequest authenticateUser(User user) throws Exception {
        User foundUser = authRepository.findUserByUsernameAndPassword(user.getUsername(), CryptoManager.encrypt(user.getPassword()));
        if(foundUser == null){
            throw new Exception("User.not.found");
        }

        String token = JwtManager.newJwt(foundUser);

        UserAuthRequest returnData = new UserAuthRequest();
        returnData.setId(foundUser.getId());
        returnData.setUsername(foundUser.getUsername());
        returnData.setEmail(foundUser.getEmail());
        returnData.setToken(token);

        return returnData;
    }

    public Long getUserIdFromToken(String token) throws Exception {
        Boolean isValid = JwtManager.isTokenValid(token);
        if(!isValid){
            throw new Exception("Invalid.token");
        }

        Claims tokenClaims = JwtManager.getJwtClaims(token);
        if(tokenClaims.get("userId") == null){
            throw new Exception("Invalid.token");
        }

        return (Long) tokenClaims.get("userId");
    }
}
