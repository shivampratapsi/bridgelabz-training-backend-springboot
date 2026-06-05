package com.fundoo.fundoonotes.exception;


import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(Exception exception) {
        exception.printStackTrace();
        ResponseDTO errorMsg= new ResponseDTO(exception.getMessage(),null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseDTO> handleUserException(UserException userException) {

        ResponseDTO errorMsg= new ResponseDTO(userException.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

    @ExceptionHandler(NoteException.class)
    public ResponseEntity<ResponseDTO> handleNoteException(NoteException noteException){
        ResponseDTO errorMsg =  new ResponseDTO(noteException.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

    @ExceptionHandler(LabelException.class)
    public ResponseEntity<ResponseDTO> handleLabelException(LabelException labelException){
        ResponseDTO errorMsg = new ResponseDTO(labelException.getMessage(),null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleArgumentException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String err = "";
        for (FieldError error : fieldErrors) {
            err += error.getDefaultMessage() + " , ";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ResponseDTO(err,null));
    }



}