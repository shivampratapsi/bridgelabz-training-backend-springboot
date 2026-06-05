package com.fundoo.fundoonotes.service.impl;

import com.fundoo.fundoonotes.dto.request.LabelReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.exception.LabelException;
import com.fundoo.fundoonotes.exception.NoteException;
import com.fundoo.fundoonotes.exception.UserException;
import com.fundoo.fundoonotes.model.Label;
import com.fundoo.fundoonotes.model.Note;
import com.fundoo.fundoonotes.model.User;
import com.fundoo.fundoonotes.repository.LabelRepository;
import com.fundoo.fundoonotes.repository.NoteRepository;
import com.fundoo.fundoonotes.repository.UserRepository;
import com.fundoo.fundoonotes.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabelServiceImple implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseDTO createLabel(LabelReqDTO labelReqDTO ,String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw  new UserException("User not found");
        }

        Label existingLabel =labelRepository.findByLabelNameAndUser( labelReqDTO.getLabelName(),user );

        if(existingLabel != null) {
            throw new LabelException("Label already exists ");
        }

        Label label = new Label();

        label.setLabelName(labelReqDTO.getLabelName());
        label.setUser(user);

        labelRepository.save(label);

        return new ResponseDTO("Label Created Successfully", email);
    }

    @Override
    public ResponseDTO addLabelToNote(Integer noteId, Integer labelId) {

        Note note = noteRepository.findById(noteId).orElse(null);

        if(note == null) {
            throw new NoteException("Note Not Found");
        }

        Label label = labelRepository.findById(labelId).orElse(null);

        if(label == null) {
            throw new LabelException("Label not found");
        }

        if(note.getLabels() == null) {
            note.setLabels(new ArrayList<>()); // for avoiding NPE
        }

        note.getLabels().add(label);

        noteRepository.save(note);

        return new ResponseDTO("Label Added To Note", noteId);
    }

    @Override
    public ResponseDTO getAllLabels(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found");
        }

        List<Label> labels = labelRepository.findByUser(user);

        return new ResponseDTO("labels found are ", labels);
    }

    @Override
    public ResponseDTO deleteLabel(Integer labelId) {

        Label label = labelRepository.findById(labelId).orElse(null);
        if (label == null) {
            throw new LabelException("Label not found");
        }

        if (label.getNotes() != null) {
            for (Note note : new ArrayList<>(label.getNotes())) {
                note.getLabels().remove(label);
                noteRepository.save(note);
            }
        }

        labelRepository.delete(label);

        return new ResponseDTO("Label deleted ", null);
    }

    @Override
    public ResponseDTO updateLabel(Integer labelId, LabelReqDTO labelReqDTO) {
        Label label = labelRepository.findById(labelId).orElse(null);
        if (label == null) {
            throw new LabelException("Label not found");
        }
        label.setLabelName(labelReqDTO.getLabelName());
        labelRepository.save(label);
        return new ResponseDTO("Label updated successfully", labelId);
    }

    @Override
    public ResponseDTO removeLabelFromNote(Integer noteId, Integer labelId) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if(note == null) {
            throw new NoteException("Note Not Found");
        }

        Label label = labelRepository.findById(labelId).orElse(null);
        if(label == null) {
            throw new LabelException("Label not found");
        }

        if(note.getLabels() != null) {
            note.getLabels().remove(label);
            noteRepository.save(note);
        }

        return new ResponseDTO("Label Removed From Note", noteId);
    }

}