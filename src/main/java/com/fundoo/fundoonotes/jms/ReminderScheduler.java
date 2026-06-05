package com.fundoo.fundoonotes.jms;

import com.fundoo.fundoonotes.jms.producer.NotificationProducer;
import com.fundoo.fundoonotes.model.Note;
import com.fundoo.fundoonotes.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReminderScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ReminderScheduler.class);

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NotificationProducer notificationProducer;

    // Runs every minute
    @Scheduled(cron = "0 * * * * *")
    public void checkAndSendReminders() {
        LocalDateTime now = LocalDateTime.now();

        List<Note> allNotes = noteRepository.findAll();

        List<Note> dueNotes = allNotes.stream()
                .filter(note -> note.getReminder() != null && !note.isReminderSent() && !note.isTrashed() && !note.isArchived() && (note.getReminder().isBefore(now) || note.getReminder().isEqual(now)))
                .collect(Collectors.toList());

        for (Note note : dueNotes) {
            String message = "Reminder Alert! Note ID: " + note.getNoteId() 
                    + " | Title: \"" + note.getTitle() + "\" | Description: \"" + note.getDescription() 
                    + "\" for User: " + (note.getUser() != null ? note.getUser().getEmail() : "Guest");
            
            logger.info("Due reminder detected! Publishing to RabbitMQ: {}", message);
            notificationProducer.sendMessage(message);

            // Mark reminder as sent rather than clearing it from database
            note.setReminderSent(true);
            noteRepository.save(note);
        }
    }
}
