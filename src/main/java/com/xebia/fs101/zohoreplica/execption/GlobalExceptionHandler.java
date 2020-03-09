package com.xebia.fs101.zohoreplica.execption;

import com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.OK;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(Exception e){
        ZohoReplicaResponse zohoReplicaResponse=new ZohoReplicaResponse.Builder()
                .withMessage(e.toString())
                .withStatus(ApplicationConstant.TXN_FAAILED)
                .build();
        return new ResponseEntity<>(zohoReplicaResponse,OK);
    }
}
