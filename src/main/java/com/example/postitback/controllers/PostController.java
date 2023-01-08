package com.example.postitback.controllers;

import com.example.postitback.entities.Posts;
import com.example.postitback.entities.User;
import com.example.postitback.pojo.PostRequest;
import com.example.postitback.repositories.PostRepository;
import com.example.postitback.services.PostService;
import com.example.postitback.utils.JwtManager;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Controller
@RequestMapping("/controllers/post")
public class PostController {
    @Autowired
    PostRepository repository;

    @Autowired
    PostService postService;

    @GetMapping
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<?> list(){
        try{
            return new ResponseEntity<Object>(postService.getPosts(), new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Transactional
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<?> post(@RequestBody PostRequest postRequest){
        try{
            Posts postResult = postService.addPost(postRequest);

            return new ResponseEntity<Object>(postResult, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @CrossOrigin("http://localhost:3000")
    ResponseEntity<?> deleteById(@RequestParam("id") Long id){
        try{
            repository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
