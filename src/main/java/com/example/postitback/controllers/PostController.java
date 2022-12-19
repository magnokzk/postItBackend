package com.example.postitback.controllers;

import com.example.postitback.entities.Posts;
import com.example.postitback.pojo.PostRequest;
import com.example.postitback.repositories.PostRepository;
import com.example.postitback.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/controllers/post")
public class PostController {
    @Autowired
    PostRepository repository;

    @Autowired
    PostService postService;

    @GetMapping
    @CrossOrigin("http://localhost:3000")
    private ResponseEntity<?> list(){
        try{
            return new ResponseEntity<Object>(postService.getPosts(), new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @CrossOrigin("http://localhost:3000")
    private ResponseEntity<?> post(@RequestBody PostRequest postRequest){
        try{
            Posts postResult = postService.addPost(postRequest);

            return new ResponseEntity<Object>(postResult, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @CrossOrigin("http://localhost:3000")
    private ResponseEntity<?> deleteById(@RequestBody Posts post){
        try{
            repository.deleteById(post.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
