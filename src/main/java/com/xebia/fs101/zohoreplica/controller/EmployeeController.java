package com.xebia.fs101.zohoreplica.controller;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.execption.EmptyFileException;
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

        Employee savedEmployee = employeeService.save(userRequest.toUser());
        ZohoReplicaResponse zohoReplicaResponse =
                new ZohoReplicaResponse.Builder()
                        .withStatus(ApplicationConstant.TXN_SUCESS)
                        .withData(savedEmployee)
                        .withMessage("User saved successfully")
                        .build();
        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @GetMapping("/profiles/{username}")
    public ResponseEntity<?> viewUser(@PathVariable(value = "username") String username) {
        Employee employee = employeeService.findByName(username);
        ZohoReplicaResponse zohoReplicaResponse = new ZohoReplicaResponse.Builder()
                .withStatus(ApplicationConstant.TXN_SUCESS)
                .withData(employee)
                .build();
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }


    @PostMapping("/profiles/upload/{username}")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file,
                                         @PathVariable(value = "username") String username) throws IOException {

        if(file.isEmpty())
            throw  new EmptyFileException("Empty file can't be uploaded");
        String fileName=file.getOriginalFilename();
        byte[] photo = file.getBytes();
        Employee employee = employeeService.findByName(username);
        employee.setPhoto(photo);
        Employee savedEmployee = employeeService.save(employee);
        ZohoReplicaResponse zohoReplicaResponse=new ZohoReplicaResponse.Builder()
                .withMessage(fileName+" uploaded")
                .withStatus(ApplicationConstant.TXN_BAD_REQUEST)
                .build();
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }

}