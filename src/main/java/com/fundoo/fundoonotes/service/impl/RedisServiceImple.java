package com.fundoo.fundoonotes.service.impl;


import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImple implements RedisService {

    @Autowired
    private RedisTemplate < String, String > redisTemplate;

    @Override
    public void saveToken(String email, String token) {
        redisTemplate.opsForValue().set(email, token,1 , TimeUnit.HOURS);

    }

    @Override
    public ResponseDTO fetchToken(String email) {
        String token = redisTemplate.opsForValue().get(email);

        return new ResponseDTO("Token Fetched Successfully",token );
    }

    public void deleteToken(String email){
        redisTemplate.delete(email);

    }

}