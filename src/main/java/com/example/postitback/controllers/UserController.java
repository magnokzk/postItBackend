package com.example.postitback.controllers;

import com.example.postitback.entities.FriendRequests;
import com.example.postitback.entities.User;
import com.example.postitback.entities.UserFriends;
import com.example.postitback.repositories.FriendRequestsRepository;
import com.example.postitback.repositories.UserFriendsRepository;
import com.example.postitback.repositories.UserRepository;
import com.example.postitback.services.UserService;
import com.example.postitback.utils.CryptoManager;
import com.example.postitback.utils.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/controllers/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    FriendRequestsRepository friendRequestsRepository;

    @Autowired
    UserFriendsRepository userFriendsRepository;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> getUserByToken(HttpServletRequest request){
        try{
            User user = JwtManager.getUserFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));

            return new ResponseEntity<Object>(repository.findById(user.getId()), new HttpHeaders(), HttpStatus.OK); 
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getUserById(@PathVariable("id") Integer id){
        try{
            Optional<User> user = repository.findById(id);
            
            return new ResponseEntity<Object>(user, new HttpHeaders(), HttpStatus.OK);   
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/hasFriendRequest/{secondId}", method = RequestMethod.GET)
    ResponseEntity<?> getUserById(@PathVariable("id") Integer id, @PathVariable("secondId") Integer secondId){
        try{
            Boolean hasPendingRequest = userService.hasPendingFriendRequest(id, secondId);
            
            return new ResponseEntity<Boolean>(hasPendingRequest, new HttpHeaders(), HttpStatus.OK);   
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "/{id}/friendList", method = RequestMethod.GET)
    ResponseEntity<?> getFriendList(@PathVariable("id") Integer id){
        try{
            List<UserFriends> userFriends = userFriendsRepository.findAllByUserId(id);
            
            return new ResponseEntity<Object>(userFriends, new HttpHeaders(), HttpStatus.OK);   
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/friendRequests", method = RequestMethod.GET)
    ResponseEntity<?> getFriendRequests(HttpServletRequest request){
        try{
            User user = JwtManager.getUserFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));

            List<FriendRequests> friendRequests = friendRequestsRepository.findFrientRequestsByToUserId(user.getId());

            return new ResponseEntity<Object>(friendRequests, new HttpHeaders(), HttpStatus.OK);    
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    ResponseEntity<?> addFriend(@RequestBody FriendRequests friendRequests){
        try{
            FriendRequests savedRequest = friendRequestsRepository.save(friendRequests);

            return new ResponseEntity<Object>(savedRequest, new HttpHeaders(), HttpStatus.OK);    
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/friendRequests/delete", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteFriendRequest(HttpServletRequest request, @RequestBody FriendRequests friendRequest){
        try{
            friendRequestsRepository.delete(friendRequest);

            return new ResponseEntity<>(HttpStatus.OK);    
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/friendRequests/send/{toUserId}", method = RequestMethod.POST)
    ResponseEntity<?> createFriendRequest(HttpServletRequest request, @PathVariable("toUserId") Integer toUserId){
        try{
            User user = JwtManager.getUserFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));

            if(toUserId == user.getId()) {
                throw new Exception("Cannot.be.friends.with.self");
            }

            FriendRequests newFriendRequest = new FriendRequests();
            newFriendRequest.setFromUserId(user.getId());
            newFriendRequest.setToUserId(toUserId);
            
            friendRequestsRepository.save(newFriendRequest);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/friendRequests/accept", method = RequestMethod.POST)
    ResponseEntity<?> acceptRequest(@RequestBody FriendRequests friendRequest){
        try{
            userService.acceptFriendRequest(friendRequest);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @RequestMapping(value = "/findByName", method = RequestMethod.POST)
    ResponseEntity<?> getUsersByName(@RequestBody User user){
        try{
            List<User> users = repository.searchUsersByUsername(user.getUsername());

            return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
