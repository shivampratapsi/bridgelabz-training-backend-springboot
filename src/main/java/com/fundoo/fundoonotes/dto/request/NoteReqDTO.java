package com.fundoo.fundoonotes.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteReqDTO {


    @Size(max = 50, message = "Title cannot exceed 50 characters")
    private String title;

    @Size(max = 255, message = "Description is too long (maximum 255 characters)")
    private String description;

    private java.time.LocalDateTime reminder;
    
    private java.util.List<Integer> labelIds;
}