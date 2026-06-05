package com.fundoo.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoo.fundoonotes.dto.request.ReminderReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.service.ReminderService;

@RestController
@RequestMapping("/reminder")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping
    public ResponseEntity<ResponseDTO> sendReminder(@RequestBody ReminderReqDTO reminderReqDTO ) {

        ResponseDTO response=reminderService.sendReminder(reminderReqDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(response);

    }
}