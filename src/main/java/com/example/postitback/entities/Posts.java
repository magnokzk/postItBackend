package com.example.postitback.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "user_id", nullable = false)
    @JsonBackReference
    private Long userId;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JsonManagedReference
    @Transient
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;
}
