package com.fundoo.fundoonotes.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Data
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer labelId;

    private String labelName;

    @ManyToMany(mappedBy = "labels")
    @JsonIgnore
    private List<Note> notes;

    @ManyToOne
    private User user;
}
