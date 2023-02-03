package com.example.postitback.repositories;

import com.example.postitback.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u WHERE LOWER(u.userName) LIKE %?1% OR LOWER(u.name) LIKE %?1% OR LOWER(u.surname) LIKE %?1%", nativeQuery = false)
    List<User> searchUsersByUserName(String userName);
}
