package com.example.postitback.repositories;

import com.example.postitback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Integer> {
    User findUserByUserNameAndPassword(String userName, String password);

    User findUserByUserName(String userName);
}
