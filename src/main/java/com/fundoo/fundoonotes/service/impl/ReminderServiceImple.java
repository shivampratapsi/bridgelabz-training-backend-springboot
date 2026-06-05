package com.fundoo.fundoonotes.service.impl;


import com.fundoo.fundoonotes.dto.request.ReminderReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.jms.producer.NotificationProducer;
import com.fundoo.fundoonotes.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderServiceImple implements ReminderService {

    @Autowired
    private NotificationProducer notificationProducer;

    @Override
    public ResponseDTO sendReminder(ReminderReqDTO reminderReqDTO ) {
        //from here the request goes to reminder controller

        notificationProducer.sendMessage(reminderReqDTO.getReminderMessage() );

        return new ResponseDTO("Reminder sent successfully to ",null );
    }
}