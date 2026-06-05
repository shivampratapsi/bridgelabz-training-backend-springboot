package com.fundoo.fundoonotes.controller;

import com.fundoo.fundoonotes.dto.request.LoginReqDTO;
import com.fundoo.fundoonotes.dto.request.RegisterReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.service.UserService;
import com.fundoo.fundoonotes.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody RegisterReqDTO registerReqDTO){
      ResponseDTO response=userService.userRegister(registerReqDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody LoginReqDTO loginReqDTO){
        ResponseDTO response =userService.userLogin(loginReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ResponseDTO> logoutUser(HttpServletRequest request) {
        String email = tokenUtil.extractEmailFromRequest(request);

        ResponseDTO response = userService.userLogout(email);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgotPassword(@RequestParam String email) {
        ResponseDTO response = userService.initiatePasswordReset(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        ResponseDTO response = userService.resetPassword(token, newPassword);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
