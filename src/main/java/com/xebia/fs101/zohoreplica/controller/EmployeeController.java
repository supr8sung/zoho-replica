package com.xebia.fs101.zohoreplica.controller;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.exception.EmptyFileException;
import com.xebia.fs101.zohoreplica.security.AdminOnly;
import com.xebia.fs101.zohoreplica.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_BAD_REQUEST;
import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_SUCESS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@RestController
@EnableEncryptableProperties
@RequestMapping(value = "/zoho")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRequest userRequest) {

        Employee savedEmployee = employeeService.save(userRequest);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "User saved successfully",
                savedEmployee);
        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @GetMapping("/profiles/{username}")
    @AdminOnly
    public ResponseEntity<?> viewUser(@PathVariable(value = "username") String username) {
        Employee employee = employeeService.findByName(username);
        ZohoReplicaResponse zohoReplicaResponse = employee != null ?
                getResponse(TXN_SUCESS, null, employee) :
                getResponse(TXN_BAD_REQUEST, "No user found", null);
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }


    @PostMapping("/profiles/upload/{username}")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file,
                                         @PathVariable(value = "username") String username) throws IOException {
        if (file.isEmpty())
            throw new EmptyFileException("Empty file can't be uploaded");
        byte[] photo = file.getOriginalFilename().getBytes();
        Employee employee = employeeService.findByName(username);
        employee.setPhoto(photo);
        employeeService.save(employee);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "profile picture uploaded",
                null);
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }

    private ZohoReplicaResponse getResponse(String status, String message, Object data) {
        return new ZohoReplicaResponse.Builder()
                .withData(data)
                .withStatus(status)
                .withMessage(message)
                .build();
    }

}
