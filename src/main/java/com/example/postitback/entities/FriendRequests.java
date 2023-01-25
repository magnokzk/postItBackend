package com.example.postitback.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FriendRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestId")
    private Integer requestId;

    @Column(name = "fromUserId", nullable = false)
    private Integer fromUserId;

    @Column(name = "toUserId", nullable = false)
    private Integer toUserId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fromUserId", insertable = false, updatable = false)
    private User fromUserInfo;
}
