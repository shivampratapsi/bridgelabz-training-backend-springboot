package com.fundoo.fundoonotes.repository;

import com.fundoo.fundoonotes.model.Note;
import com.fundoo.fundoonotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> {

        Note findByTitle(String title);
        Optional<Note> findFirstByUserOrderByNoteId(User user); // for new note
        List<Note> findByUser(User user);

        List<Note> findByTitleContaining(String title);
        List<Note> findByTitleContainingIgnoreCase(String title);
        List<Note> findByTitleContainingIgnoreCaseAndUser(String title, User user);

        List<Note> findByIsTrashedTrueAndUpdatedAtBefore(java.time.LocalDateTime threshold);
}
