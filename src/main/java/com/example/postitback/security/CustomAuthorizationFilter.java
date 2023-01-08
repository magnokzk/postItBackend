package com.example.postitback.security;

import com.example.postitback.utils.JwtManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(request.getServletPath().equals("/controllers/auth") || request.getServletPath().equals("/controllers/user/register")){
                filterChain.doFilter(request, response);
                return;
            }

            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null){
                throw new Exception("authorization.not.found");
            }

            if(JwtManager.isTokenValid(authorizationHeader)){
                throw new Exception("unauthorized");
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setHeader("error", e.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            System.out.println(e.getMessage());
        }
    }
}
