package com.example.postitback.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postitback.entities.UserFriends;

public interface UserFriendsRepository extends JpaRepository<UserFriends, Integer> {
    List<UserFriends> findAllByUserId(Integer userId);
}
