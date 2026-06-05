package com.fundoo.fundoonotes.service;


import com.fundoo.fundoonotes.dto.response.ResponseDTO;

public interface RedisService {
//    String getToken(String email);

    void saveToken(String email, String token);

    ResponseDTO fetchToken(String email);

    void deleteToken(String email);


}