package com.example.postitback.repositories;

import com.example.postitback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Integer> {
    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);
}
