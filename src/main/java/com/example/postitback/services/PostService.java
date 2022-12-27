package com.example.postitback.services;

import com.example.postitback.entities.Posts;
import com.example.postitback.entities.User;
import com.example.postitback.pojo.PostRequest;
import com.example.postitback.repositories.PostRepository;
import com.example.postitback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PostService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public Posts addPost(PostRequest postRequest) throws Exception {
        Posts post = new Posts();
        post.setDescription(postRequest.description);
        post.setUserId(postRequest.userId);

        Posts savedPost = postRepository.save(post);
        if(savedPost.getId() == null){
            throw new Exception("unable.to.save.post");
        }

        userRepository.findById(post.getUserId()).ifPresentOrElse(
                savedPost::setUser,
                () -> {throw new RuntimeException("user.non.existant");}
        );

        return savedPost;
    }

    public List<Posts> getPosts(){
        List<Posts> allPosts = postRepository.findAll();
        for(Posts post : allPosts){
            Optional<User> userOptional = userRepository.findById(post.getUserId());
            userOptional.ifPresent(post::setUser);
        }

        return allPosts;
    }

}
