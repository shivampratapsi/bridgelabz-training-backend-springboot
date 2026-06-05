package com.fundoo.fundoonotes.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginReqDTO {

    @NotBlank(message = "Email should be there")
    @Email(message = "email format not valid")
    @Size(min = 5, max = 40, message = "Email must be between 5 and 40 characters")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;
}