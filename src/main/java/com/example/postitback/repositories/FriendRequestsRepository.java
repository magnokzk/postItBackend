package com.example.postitback.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postitback.entities.FriendRequests;

public interface FriendRequestsRepository extends JpaRepository<FriendRequests, Integer> {
    List<FriendRequests> findFrientRequestsByToUserId(Integer userId);    
}
