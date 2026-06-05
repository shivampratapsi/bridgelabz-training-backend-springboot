package com.fundoo.fundoonotes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteReqDTO {

    // @NotBlank(message = "Title is must")
    @Size(max = 50, message = "Title cannot exceed 50 characters")
    private String title;

    // @NotBlank(message = "Description is must for note")
    @Size(max = 255, message = "Description is too long (maximum 255 characters)")
    private String description;

    private java.time.LocalDateTime reminder;
    
    private java.util.List<Integer> labelIds;
}