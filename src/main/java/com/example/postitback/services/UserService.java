package com.example.postitback.services;

import com.example.postitback.entities.FriendRequests;
import com.example.postitback.entities.User;
import com.example.postitback.entities.UserFriends;
import com.example.postitback.pojo.UserAuthRequest;
import com.example.postitback.repositories.AuthRepository;
import com.example.postitback.repositories.FriendRequestsRepository;
import com.example.postitback.repositories.UserFriendsRepository;
import com.example.postitback.repositories.UserRepository;
import com.example.postitback.utils.CryptoManager;
import com.example.postitback.utils.JwtManager;
import io.jsonwebtoken.Claims;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    UserFriendsRepository userFriendsRepository;

    @Autowired
    FriendRequestsRepository friendRequestsRepository;

    public UserAuthRequest authenticateUser(User user) throws Exception {
        User foundUser = authRepository.findUserByUserNameAndPassword(user.getUserName(), CryptoManager.encrypt(user.getPassword()));
        if(foundUser == null){
            throw new Exception("User.not.found");
        }

        String token = JwtManager.newJwt(foundUser);

        UserAuthRequest returnData = new UserAuthRequest();
        returnData.setId(foundUser.getId());
        returnData.setUserName(foundUser.getUserName());
        returnData.setEmail(foundUser.getEmail());
        returnData.setToken(token);

        return returnData;
    }

    @Transactional
    public void acceptFriendRequest(FriendRequests request) throws Exception {
        UserFriends recipient = new UserFriends();
        recipient.setUserId(request.getToUserId());
        recipient.setFriendId(request.getFromUserId());

        userFriendsRepository.save(recipient);

        UserFriends sender = new UserFriends();
        sender.setUserId(request.getFromUserId());
        sender.setFriendId(request.getToUserId());

        userFriendsRepository.save(sender);

        friendRequestsRepository.delete(request);
    }

    public Boolean hasPendingFriendRequest(Integer firstId, Integer secondId){
        FriendRequests firstFetch = friendRequestsRepository.findByToUserIdAndFromUserId(firstId, secondId);
        if(firstFetch != null) {
            return true;
        }

        FriendRequests secondFetch = friendRequestsRepository.findByToUserIdAndFromUserId(secondId, firstId);
        if(secondFetch != null) {
            return true;
        }

        return false;
    }

    public Integer getUserIdFromToken(String token) throws Exception {
        Boolean isValid = JwtManager.isTokenValid(token);
        if(!isValid){
            throw new Exception("Invalid.token");
        }

        Claims tokenClaims = JwtManager.getJwtClaims(token);
        if(tokenClaims.get("userId") == null){
            throw new Exception("Invalid.token");
        }

        return (Integer) tokenClaims.get("userId");
    }
}
