package com.fundoo.fundoonotes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundoo.fundoonotes.dto.request.NoteReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.exception.NoteException;
import com.fundoo.fundoonotes.exception.UserException;
import com.fundoo.fundoonotes.model.Label;
import com.fundoo.fundoonotes.model.Note;
import com.fundoo.fundoonotes.model.User;
import com.fundoo.fundoonotes.repository.LabelRepository;
import com.fundoo.fundoonotes.repository.NoteRepository;
import com.fundoo.fundoonotes.repository.UserRepository;
import com.fundoo.fundoonotes.service.NoteService;

@Service
public class NoteServiceImple implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImple.class);


    @Override
    public ResponseDTO createNote(NoteReqDTO noteRequestDTO, String email) {

        logger.info("Note creation started");
        User user = userRepository.findByEmail(email);

        Note note = new Note();

        note.setTitle(noteRequestDTO.getTitle());
        note.setDescription(noteRequestDTO.getDescription());
        note.setUser(user);

        if (noteRequestDTO.getReminder() != null) {
            note.setReminder(noteRequestDTO.getReminder());
            note.setReminderSent(false);
        }

        if (noteRequestDTO.getLabelIds() != null && !noteRequestDTO.getLabelIds().isEmpty()) {
            List<Label> labels = labelRepository.findAllById(noteRequestDTO.getLabelIds());
            note.setLabels(labels);
        }

        Note savedNote = noteRepository.save(note);
        logger.info("Note saved");

        return new ResponseDTO("Note Created Successfully", savedNote);
    }

    @Override
    public ResponseDTO getAllNotes(String email) {

        User user = userRepository.findByEmail(email);

        List<Note> notes = noteRepository.findByUser(user);

        return new ResponseDTO("All Notes", notes);
    }

    @Override
    public ResponseDTO pinNote(Integer noteId) {

        Note note = noteRepository.findById(noteId).orElse(null);

        if(note == null) {
            throw new NoteException("No note Found for pinning");
        }

        note.setPinned(true);

        noteRepository.save(note);

        return new ResponseDTO("Note Pinned Successfully", noteId);
    }

    @Override
    public ResponseDTO archiveNote(Integer noteId) {

        Note note = noteRepository.findById(noteId).orElse(null);

        if(note == null) {throw new NoteException("No note found for archiving");
        }

        note.setArchived(true);

        noteRepository.save(note);

        return new ResponseDTO("Note Archived Successfully", noteId);
    }

    @Override
    public ResponseDTO trashNote(Integer noteId) {

        Note note = noteRepository.findById(noteId).orElse(null);

        if(note == null) {
            throw new NoteException("No note for trash");
        }

        note.setTrashed(true);

        noteRepository.save(note);

        return new ResponseDTO("Note Trashed Successfully", noteId);
    }
    @Override
    public ResponseDTO searchNote(String title, String email){
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found");
        }

        List<Note> notes = noteRepository.findByTitleContainingIgnoreCaseAndUser(title, user);

        if (notes == null){
            throw new NoteException("Note not found with this title");
        }

        return  new ResponseDTO("Searched notes  found  ", notes);
    }

    @Override
    public ResponseDTO unPinNote(Integer noteId){

        Note note = noteRepository.findById(noteId).orElse(null);

        if (note == null) {
            throw new NoteException("Note not found");
        }

        note.setPinned(false);
        noteRepository.save(note);

        return new ResponseDTO("Note unpinned ", noteId);
    }

    @Override
    public ResponseDTO unArchiveNote(Integer noteId){
        Note note = noteRepository.findById(noteId).orElse(null);

        if (note == null) {
            throw new NoteException("Note not found");
        }

        note.setArchived(false);
        noteRepository.save(note);
        return new ResponseDTO("Note Unarchived ", noteId);
    }

    @Override
    public ResponseDTO restoreNote(Integer noteId) {

        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) {
            throw new NoteException("Note not found");
        }

        note.setTrashed(false);
        noteRepository.save(note);

        return new ResponseDTO("Note restored ", noteId);
    }

    @Override
    public ResponseDTO deleteNote(Integer noteId){
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) {
            throw new NoteException("Note not found");
        }


        noteRepository.delete(note);
        return new ResponseDTO("Note deleted ", noteId);
    }

    @Override
    public ResponseDTO updateNote(Integer noteId, NoteReqDTO noteRequestDTO) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) {
            throw new NoteException("Note not found");
        }
        note.setTitle(noteRequestDTO.getTitle());
        note.setDescription(noteRequestDTO.getDescription());
        noteRepository.save(note);
        return new ResponseDTO("Note updated successfully", noteId);
    }

    @Override
    public ResponseDTO setReminder(Integer noteId, LocalDateTime reminderTime) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) {
            throw new NoteException("Note not found");
        }
        note.setReminder(reminderTime);
        note.setReminderSent(false);
        noteRepository.save(note);
        return new ResponseDTO("Reminder set successfully", noteId);
    }

    @Override
    public ResponseDTO removeReminder(Integer noteId) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) {
            throw new NoteException("Note not found");
        }
        note.setReminder(null);
        note.setReminderSent(false);
        noteRepository.save(note);
        return new ResponseDTO("Reminder removed successfully", noteId);
    }

}