package com.fundoo.fundoonotes.service;

import com.fundoo.fundoonotes.dto.request.LabelReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;


public interface LabelService {
    ResponseDTO createLabel(LabelReqDTO labelReqDTO, String email);

    ResponseDTO addLabelToNote(Integer noteId, Integer labelId);

    ResponseDTO getAllLabels(String email);

    ResponseDTO deleteLabel(Integer labelId);

    ResponseDTO updateLabel(Integer labelId, LabelReqDTO labelReqDTO);

    ResponseDTO removeLabelFromNote(Integer noteId, Integer labelId);
}

