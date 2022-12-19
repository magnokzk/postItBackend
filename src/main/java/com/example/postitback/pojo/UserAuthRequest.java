package com.example.postitback.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthRequest {
    private Long id;
    private String username;
    private String email;
    private String token;
}
