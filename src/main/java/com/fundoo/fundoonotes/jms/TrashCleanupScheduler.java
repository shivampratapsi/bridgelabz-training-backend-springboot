package com.fundoo.fundoonotes.jms;

import com.fundoo.fundoonotes.model.Note;
import com.fundoo.fundoonotes.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TrashCleanupScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TrashCleanupScheduler.class);

    @Autowired
    private NoteRepository noteRepository;

    // Runs every day at midnight (0 0 0 * * *)
    // Standard cron expression: "second minute hour day-of-month month day-of-week"
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanExpiredTrashNotes() {
        logger.info("Starting scheduled auto-delete of expired trashed notes...");
        LocalDateTime threshold = LocalDateTime.now().minusDays(7);

        List<Note> expiredNotes = noteRepository.findByIsTrashedTrueAndUpdatedAtBefore(threshold);

        if (expiredNotes != null && !expiredNotes.isEmpty()) {
            logger.info("Found {} expired trashed notes to delete.", expiredNotes.size());
            noteRepository.deleteAll(expiredNotes);
            logger.info("Successfully deleted expired trashed notes.");
        } else {
            logger.info("No expired trashed notes found to delete.");
        }
    }
}
