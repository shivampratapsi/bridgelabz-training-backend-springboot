package com.fundoo.fundoonotes.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LabelReqDTO {

    @NotBlank(message = "Label name required")
    private String labelName;
}