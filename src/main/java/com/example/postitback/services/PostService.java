package com.example.postitback.services;

import com.example.postitback.entities.Posts;
import com.example.postitback.entities.User;
import com.example.postitback.pojo.PostRequest;
import com.example.postitback.repositories.PostRepository;
import com.example.postitback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public PostService(){

    }

    public Posts addPost(PostRequest postRequest){
        Optional<User> user = userRepository.findById(postRequest.user_id);

        Posts post = new Posts();
        post.setDescription(postRequest.description);
        user.ifPresent(userValue -> post.setUser(userValue));

        return postRepository.save(post);
    }

}
