package com.example.postitback.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.postitback.entities.UserFriends;

public interface UserFriendsRepository extends JpaRepository<UserFriends, Integer> {
    List<UserFriends> findAllByUserId(Integer userId);

    @Query(value = "SELECT uf.friendId FROM UserFriends uf WHERE uf.userId = :userId")
    List<Integer>  findFriendsIdByUserId(@Param("userId") Integer userId);
}
