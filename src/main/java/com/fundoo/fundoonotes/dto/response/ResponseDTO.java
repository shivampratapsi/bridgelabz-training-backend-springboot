package com.fundoo.fundoonotes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO {

    private  String message;

    private Object data; // for returning user data

}
