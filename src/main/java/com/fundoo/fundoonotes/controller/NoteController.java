package com.fundoo.fundoonotes.controller;

import com.fundoo.fundoonotes.dto.request.NoteReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.service.NoteService;
import com.fundoo.fundoonotes.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private  NoteService noteService;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createNote(HttpServletRequest request , @Valid @RequestBody NoteReqDTO noteRequestDTO){
        String email = tokenUtil.extractEmailFromRequest(request);

        ResponseDTO response=  noteService.createNote(noteRequestDTO, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/allnotes")
    public ResponseEntity<ResponseDTO> getAllNotes(HttpServletRequest request) {

        String email = tokenUtil.extractEmailFromRequest(request);

        ResponseDTO response=noteService.getAllNotes(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/pin/{noteId}")
    public ResponseEntity<ResponseDTO> pinNote(@PathVariable Integer noteId) {

        ResponseDTO response=noteService.pinNote(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/unpin/{noteId}")
    public ResponseEntity<ResponseDTO> unPinNote(@PathVariable Integer noteId){
        ResponseDTO response = noteService.unPinNote(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/archive/{noteId}")
    public ResponseEntity<ResponseDTO> archiveNote(@PathVariable Integer noteId) {

        ResponseDTO response=noteService.archiveNote(noteId);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/unarchive/{noteId}")
    public ResponseEntity<ResponseDTO> unArchiveNote(@PathVariable Integer noteId) {
        ResponseDTO response = noteService.unArchiveNote(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/trash/{noteId}")
    public ResponseEntity<ResponseDTO> trashNote(@PathVariable Integer noteId) {

        ResponseDTO response=noteService.trashNote(noteId);
        return  ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/restore/{noteId}")
    public ResponseEntity<ResponseDTO> restoreNote(@PathVariable Integer noteId) {
        ResponseDTO response = noteService.restoreNote(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


// where we give title in search
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchNotes(HttpServletRequest request, @RequestParam String title) {
        String email = tokenUtil.extractEmailFromRequest(request);
        ResponseDTO response= noteService.searchNote(title, email);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity<ResponseDTO> deleteNote(@PathVariable Integer noteId) {
        ResponseDTO response = noteService.deleteNote(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{noteId}")
    public ResponseEntity<ResponseDTO> updateNote(@PathVariable Integer noteId, @Valid @RequestBody NoteReqDTO noteReqDTO) {
        ResponseDTO response = noteService.updateNote(noteId, noteReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // new mapping
    @GetMapping("/searchByTitle/{title}")
    public ResponseDTO searchNoteByTitle(HttpServletRequest request, @PathVariable String title){
        String email = tokenUtil.extractEmailFromRequest(request);
        return noteService.searchNote(title, email);
    }

    @PutMapping("/reminder/{noteId}")
    public ResponseEntity<ResponseDTO> setReminder(
            @PathVariable Integer noteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminderTime) {
        ResponseDTO response = noteService.setReminder(noteId, reminderTime);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/reminder/{noteId}")
    public ResponseEntity<ResponseDTO> removeReminder(@PathVariable Integer noteId) {
        ResponseDTO response = noteService.removeReminder(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}