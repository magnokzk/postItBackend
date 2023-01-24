package com.example.postitback.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthRequest {
    private Integer id;
    private String username;
    private String email;
    private String token;
}
