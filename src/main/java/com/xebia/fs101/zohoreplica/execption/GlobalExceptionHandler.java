package com.xebia.fs101.zohoreplica.execption;

import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_BAD_REQUEST;
import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_FAILED;
import static org.springframework.http.HttpStatus.OK;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(Exception e) {
        ZohoReplicaResponse zohoReplicaResponse = new ZohoReplicaResponse.Builder()
                .withMessage(e.toString())
                .withStatus(TXN_FAILED)
                .build();
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<?> emptyFile(Exception e){
        ZohoReplicaResponse zohoReplicaResponse=new ZohoReplicaResponse.Builder()
                .withMessage(e.toString())
                .withStatus(TXN_BAD_REQUEST)
                .build();
        return new ResponseEntity<>(zohoReplicaResponse,OK);
    }
}
