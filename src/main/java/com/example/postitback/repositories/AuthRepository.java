package com.example.postitback.repositories;

import com.example.postitback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User,Long> {
    User findUserByUsernameAndPassword(String userna, String password);

    User findUserByUsername(String email);
}
