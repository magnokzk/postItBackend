package com.example.postitback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserFriends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer relationId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer friendId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User friendInfo;
}
