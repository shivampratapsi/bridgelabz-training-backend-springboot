package com.fundoo.fundoonotes.service;

import com.fundoo.fundoonotes.dto.request.ReminderReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;

public interface ReminderService {

    ResponseDTO sendReminder(ReminderReqDTO reminderReqDTO);

}
