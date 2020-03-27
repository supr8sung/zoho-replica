package com.xebia.fs101.zohoreplica.controller;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.xebia.fs101.zohoreplica.api.request.ChangePasswordRequest;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.exception.EmptyFileException;
import com.xebia.fs101.zohoreplica.security.AdminOnly;
import com.xebia.fs101.zohoreplica.service.EmployeeService;
import com.xebia.fs101.zohoreplica.service.MailService;
import com.xebia.fs101.zohoreplica.utility.MailUtility;
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
import static com.xebia.fs101.zohoreplica.utility.OtpUtility.generateOtp;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@RestController
@EnableEncryptableProperties
@RequestMapping(value = "/zoho")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MailService mailService;


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
                getResponse(TXN_SUCESS, "", employee) :
                getResponse(TXN_BAD_REQUEST, "No user found", "");
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }

    @GetMapping("/profiles/{username}/forgotpasword")
    public ResponseEntity<?> forgotPasswordMail(@PathVariable(value = "username") String username){
        Employee employee = employeeService.findByName(username);
        String otp=generateOtp();
        String mailBody = MailUtility.generateOtpBody(otp, employee.getFullname());
        mailService.sendMail(employee.getEmail(),mailBody);
        ZohoReplicaResponse zohoReplicaResponse=getResponse(TXN_SUCESS,"Mail Sent Successfully",otp);
        return new ResponseEntity<>(zohoReplicaResponse,OK);

    }
    @PostMapping("profiles/{username}/recoverpassword")
    public ResponseEntity<?> recoverPassword(@PathVariable(value = "username")String  username,
                                             @Valid@RequestBody ChangePasswordRequest request){
        Employee employee = employeeService.findByName(username);
        Employee savedEmployee = employeeService.changePassword(employee, request.getNewPassword());
        ZohoReplicaResponse zohoReplicaResponse=getResponse(TXN_SUCESS,"Password Changes Successfully",
                savedEmployee);
        return new ResponseEntity<>(zohoReplicaResponse,OK);
    }
    @PostMapping("/profiles/{username}/changepassword")
    public ResponseEntity<?> changePassword(@PathVariable(value = "username") String username ,
                                            @Valid @RequestBody ChangePasswordRequest request){
        Employee employee = employeeService.findByName(username,request.getOldPassword());
        Employee savedEmployee = employeeService.changePassword(employee, request.getNewPassword());
        ZohoReplicaResponse zohoReplicaResponse=getResponse(TXN_SUCESS,"Password changes successfully",savedEmployee);
        return new ResponseEntity<>(zohoReplicaResponse,OK);
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
