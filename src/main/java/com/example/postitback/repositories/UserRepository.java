package com.example.postitback.repositories;

import com.example.postitback.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE LOWER(u.username) LIKE %?1%", nativeQuery = false)
    List<User> searchUsersByUsername(String username);
}
