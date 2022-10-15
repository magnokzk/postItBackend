package com.example.postitback.repositories;

import com.example.postitback.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {

}
