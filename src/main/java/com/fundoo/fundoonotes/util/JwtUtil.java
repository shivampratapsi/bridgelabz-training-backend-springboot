package com.fundoo.fundoonotes.util;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey ;

    private SecretKey secureSecretKey(){
        byte[] byteKey = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(byteKey);
    }

    public String generateToken(String email) {
        long timeInHour = 60 * 60 * 1000;

        return Jwts.builder().subject(email).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + timeInHour)).signWith(secureSecretKey()).compact();
    }

    public String extractEmail(String token){
        Claims clam = Jwts.parser().verifyWith(secureSecretKey()).build().parseSignedClaims(token).getPayload();

        return clam.getSubject();

    }

//    public boolean validateToken(String token , String email){
//        String extractedEmail = extractEmail(token);
//        return extractedEmail.equals(email);
//    }

}
