package com.xebia.fs101.zohoreplica.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sun.org.apache.regexp.internal.RE;
import com.xebia.fs101.zohoreplica.api.response.GenericResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ValidationException;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_BAD_REQUEST;
import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_FAILED;
import static org.springframework.http.HttpStatus.OK;

@ControllerAdvice
public class GlobalExceptionHandler {
    private GenericResponse genericResponse;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(UserNotFoundException ex) {

        genericResponse = getResponse(TXN_FAILED, ex.toString(), "");
        return new ResponseEntity<>(genericResponse, OK);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> wrongPassword(WrongPasswordException ex) {

        genericResponse = getResponse(TXN_BAD_REQUEST, ex.getMessage(), "");
        return new ResponseEntity<>(genericResponse, OK);
    }

    @ExceptionHandler(FileNotUploadException.class)
    public ResponseEntity<?> emptyFile(FileNotUploadException ex) {

        genericResponse = getResponse(TXN_BAD_REQUEST, ex.getMessage(), "");
        return new ResponseEntity<>(genericResponse, OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> fieldNotValid(MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldError();
        genericResponse = getResponse(TXN_BAD_REQUEST,
                                      fieldError.getField() + " " + fieldError.getDefaultMessage(),
                                      "");
        return new ResponseEntity<>(genericResponse, OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> userNotSaved(DataIntegrityViolationException ex) {

        genericResponse = getResponse(TXN_FAILED, ex.getRootCause().getMessage().split("\n")[0],
                                      "");
        return new ResponseEntity<>(genericResponse, OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> invalidData(IllegalArgumentException ex) {

        genericResponse = getResponse(TXN_BAD_REQUEST, ex.getMessage(), "");
        return new ResponseEntity<>(genericResponse, OK);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> fileSizeExceed() {

        genericResponse = getResponse(TXN_BAD_REQUEST, "Please upload a file less than 2MB",
                                      "");
        return new ResponseEntity<>(genericResponse, OK);
    }


    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> invalidFormat(InvalidFormatException ex){
        genericResponse =getResponse(TXN_BAD_REQUEST, "Invalid value "+ex.getValue(), "");
        return new ResponseEntity<>(genericResponse, OK);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationFailed(ValidationException ex){
        genericResponse =getResponse(TXN_BAD_REQUEST, ex.getMessage(), "");
        return new ResponseEntity<>(genericResponse, OK);
    }




    public GenericResponse getResponse(String status, String message, Object data) {

        return new GenericResponse.Builder()
                .withMessage(message)
                .withStatus(status)
                .withData(data)
                .build();
    }
}
