package com.fundoo.fundoonotes.controller;

import com.fundoo.fundoonotes.dto.request.LabelReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.service.LabelService;
import com.fundoo.fundoonotes.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;
    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLabel(HttpServletRequest request, @RequestBody LabelReqDTO labelReqDTO ) {

        String email = tokenUtil.extractEmailFromRequest(request);

        ResponseDTO response=labelService.createLabel(labelReqDTO, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{noteId}/{labelId}")
    public ResponseEntity<ResponseDTO> addLabelToNote(@PathVariable Integer noteId,@PathVariable Integer labelId ) {

        ResponseDTO response=labelService.addLabelToNote(noteId, labelId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllLabels(HttpServletRequest request) {
        String email = tokenUtil.extractEmailFromRequest(request);
        ResponseDTO response = labelService.getAllLabels(email);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{labelId}")
    public ResponseEntity<ResponseDTO> deleteLabel(@PathVariable Integer labelId) {
        ResponseDTO response = labelService.deleteLabel(labelId);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/update/{labelId}")
    public ResponseEntity<ResponseDTO> updateLabel(@PathVariable Integer labelId, @RequestBody LabelReqDTO labelReqDTO) {
        ResponseDTO response = labelService.updateLabel(labelId, labelReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{noteId}/{labelId}")
    public ResponseEntity<ResponseDTO> removeLabelFromNote(@PathVariable Integer noteId, @PathVariable Integer labelId) {
        ResponseDTO response = labelService.removeLabelFromNote(noteId, labelId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}