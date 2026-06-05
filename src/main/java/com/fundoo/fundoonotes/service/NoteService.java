package com.fundoo.fundoonotes.service;


import com.fundoo.fundoonotes.dto.request.NoteReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import java.time.LocalDateTime;

public interface NoteService {

    ResponseDTO createNote(NoteReqDTO noteRequestDTO, String email);

    ResponseDTO getAllNotes(String email);

    ResponseDTO trashNote(Integer noteId);
    ResponseDTO pinNote(Integer noteId);

    ResponseDTO archiveNote(Integer noteId);

    ResponseDTO searchNote(String title, String email);

    ResponseDTO unPinNote(Integer noteId);


    ResponseDTO unArchiveNote(Integer noteId);

    ResponseDTO restoreNote(Integer noteId);


    ResponseDTO deleteNote(Integer noteId);

    ResponseDTO updateNote(Integer noteId, NoteReqDTO noteRequestDTO);

    ResponseDTO setReminder(Integer noteId, LocalDateTime reminderTime);

    ResponseDTO removeReminder(Integer noteId);

//    not done

//    ResponseDTO setReminder(Integer noteId);

    // ResponseDTO updateReminder(Integer noteId);

    //ResponseDTO addLabel(Integer noteId)

    //ResponseDTO getTrashhedNote(Integer noteId)

    // ResponseDTO getArchiveNote(Integer noteId)








}