package com.example.postitback.repositories;

import com.example.postitback.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Integer> {
    List<Posts> findAllByUserId(Integer id);
}
