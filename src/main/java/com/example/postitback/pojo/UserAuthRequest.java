package com.example.postitback.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthRequest {
    private Integer id;
    private String userName;
    private String email;
    private String token;
}
