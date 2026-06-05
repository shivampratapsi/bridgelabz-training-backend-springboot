package com.fundoo.fundoonotes.service;

import com.fundoo.fundoonotes.dto.request.LoginReqDTO;
import com.fundoo.fundoonotes.dto.request.RegisterReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;

public interface UserService {


     ResponseDTO  userRegister(RegisterReqDTO registerReqDTO);

     ResponseDTO userLogin(LoginReqDTO loginReqDTO);

     ResponseDTO userLogout(String email);

     ResponseDTO initiatePasswordReset(String email);

     ResponseDTO resetPassword(String token, String newPassword);

}
