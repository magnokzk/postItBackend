package com.example.postitback.utils;

import com.example.postitback.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

public class JwtManager {

    private static final String secretKey = "ov4uDub1ZUpkHMWCX3B18T7dIJK6bS/c6YKgYJBI9IekQDp1nE7OUOf3n7/nvbsVO98Zq/47DJbgZZ8NnWUqBA==";
    public static String newJwt(User user){
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .setSubject("user")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static Boolean isTokenValid(String token){
        Claims claims = getJwtClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    public static Claims getJwtClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }

    public static User getUserFromToken(String token){
        Claims claims = getJwtClaims(token);

        User claimUser = new User();
        claimUser.setId((Integer) claims.get("userId"));
        claimUser.setUsername((String) claims.get("username"));
        claimUser.setEmail((String) claims.get("email"));

        return claimUser;
    }
}
