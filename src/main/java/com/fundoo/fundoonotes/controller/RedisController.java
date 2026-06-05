package com.fundoo.fundoonotes.controller;


import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/{email}")
    public ResponseDTO fetchToken(@PathVariable String email  ) {

        return redisService.fetchToken(email);
        //this is used whenever user return to website


    }
}