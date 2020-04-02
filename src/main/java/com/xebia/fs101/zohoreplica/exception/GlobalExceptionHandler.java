package com.xebia.fs101.zohoreplica.exception;

import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.Set;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_BAD_REQUEST;
import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_FAILED;
import static org.springframework.http.HttpStatus.OK;
@ControllerAdvice
public class GlobalExceptionHandler {

    private ZohoReplicaResponse zohoReplicaResponse;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(Exception ex) {
        zohoReplicaResponse = getResponse(TXN_FAILED, ex.toString(), "");
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> wrongPassword(Exception ex) {
        zohoReplicaResponse = getResponse(TXN_BAD_REQUEST, ex.getMessage(), "");
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<?> emptyFile(Exception ex) {
        zohoReplicaResponse = getResponse(TXN_BAD_REQUEST, ex.getMessage(), "");
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> fieldNotValid(MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldError();
        zohoReplicaResponse = getResponse(TXN_BAD_REQUEST,
                fieldError.getField() + " " + fieldError.getDefaultMessage(), "");
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> userNotSaved(DataIntegrityViolationException ex) {

        zohoReplicaResponse = getResponse(TXN_FAILED, ex.getRootCause().getMessage(), "");
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<?> invalidUser(InternalAuthenticationServiceException ex){

        zohoReplicaResponse=getResponse(TXN_BAD_REQUEST,ex.getMessage(),"");
        return new ResponseEntity<>(zohoReplicaResponse,OK);

    }

    public ZohoReplicaResponse getResponse(String status, String message, Object data) {
        return new ZohoReplicaResponse.Builder()
                .withMessage(message)
                .withStatus(status)
                .withData(data)
                .build();
    }
}
