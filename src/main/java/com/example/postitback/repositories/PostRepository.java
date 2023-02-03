package com.example.postitback.repositories;

import com.example.postitback.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Integer> {
    List<Posts> findAllByUserId(Integer id);

    @Query(value = "SELECT p FROM Posts p WHERE p.userId IN :ids OR p.userId = :userId")
    List<Posts> findAllByUserIds(@Param("ids") List<Integer> ids, @Param("userId") Integer userId);
}
