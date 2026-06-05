package com.fundoo.fundoonotes.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public  class TokenUtil {

    @Autowired
    private JwtUtil jwtUtil;

    public  String extractEmailFromRequest(HttpServletRequest request){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            throw  new IllegalArgumentException("Invalid authorization");

        }
        String token = authHeader.substring(7).trim();
        return  jwtUtil.extractEmail(token);
    }
}
