package com.fundoo.fundoonotes.model;


import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data

public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;

    private String title;

    private String description;

    private boolean isTrashed;

    private boolean isPinned;

    private boolean isArchived;

    private LocalDateTime reminder;

    private boolean isReminderSent;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Label> labels;
}
