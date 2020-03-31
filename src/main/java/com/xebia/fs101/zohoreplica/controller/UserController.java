package com.xebia.fs101.zohoreplica.controller;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.xebia.fs101.zohoreplica.api.request.ChangePasswordRequest;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.FollowResponse;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.exception.EmptyFileException;
import com.xebia.fs101.zohoreplica.service.MailService;
import com.xebia.fs101.zohoreplica.service.UserService;
import com.xebia.fs101.zohoreplica.utility.MailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_SUCESS;
import static com.xebia.fs101.zohoreplica.utility.OtpUtility.generateOtp;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@RestController
@EnableEncryptableProperties
@RequestMapping(value = "/zoho")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRequest userRequest) {

        User savedUser = userService.save(userRequest);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "User saved successfully",
                savedUser.getUsername());
        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> view(@PathVariable(value = "username") String username) {
        User user = userService.findByName(username);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "", user.toUserViewResponse());
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }

    @GetMapping("/user/{username}/info")
    public ResponseEntity<?> followers(@PathVariable(value = "username") String username){
        User user = userService.findByName(username);
        FollowResponse followResponse=new FollowResponse(user.getFollowersCount(),user.getFollowingCount());
        ZohoReplicaResponse zohoReplicaResponse=getResponse(TXN_SUCESS,"",followResponse);
        return new ResponseEntity<>(zohoReplicaResponse,OK);
    }



    @PutMapping("/user/edit")
    public ResponseEntity<?> edit(@Valid @RequestBody UserRequest userRequest) {
        User user = getLoggedInUser();
        userService.save(userRequest);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "", user);
        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @PostMapping("/user/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty())
            throw new EmptyFileException("Empty file can't be uploaded");
        byte[] photo = file.getOriginalFilename().getBytes();
        User user = getLoggedInUser();
        user.setPhoto(photo);
        userService.save(user);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "profile picture uploaded",
                null);
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }

    @PostMapping("/user/follow")
    public ResponseEntity<?> follow(@RequestParam String target) {
        User requestUser = getLoggedInUser();
        User targetUser = userService.findByName(target);
        String message = userService.follow(requestUser, targetUser);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, message, "");
        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }


    @PostMapping("/user/unfollow")
    public ResponseEntity<?> unfollow(@RequestParam String target) {
        User requestUser = getLoggedInUser();
        User targetUser = userService.findByName(target);
        String message = userService.unfollow(requestUser, targetUser);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, message, "");
        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @PostMapping("/user/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody ChangePasswordRequest request) {
        User user = userService.findByName(getLoggedInUser(), request.getOldPassword());
        User savedUser = userService.changePassword(user, request.getNewPassword());
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "Password changes successfully",
                savedUser);
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }

    @GetMapping("/user/send-otp")
    public ResponseEntity<?> forgotPassword() {
        User user = getLoggedInUser();
        String otp = generateOtp();
        String mailBody = MailUtility.generateOtpBody(otp, user.getFullname());
        mailService.sendMail(user.getEmail(), mailBody);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "OTP sent to your registered mail" +
                " id", otp);
        return new ResponseEntity<>(zohoReplicaResponse, OK);

    }

    @PostMapping("/user/recover-password")
    public ResponseEntity<?> recoverPassword(@PathVariable(value = "username") String username,
                                             @Valid @RequestBody ChangePasswordRequest request) {
        User user = userService.findByName(username);
        User savedUser = userService.changePassword(user, request.getNewPassword());
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "Password Changes Successfully",
                savedUser);
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }

    private ZohoReplicaResponse getResponse(String status, String message, Object data) {
        return new ZohoReplicaResponse.Builder()
                .withData(data)
                .withStatus(status)
                .withMessage(message)
                .build();
    }

    private User getLoggedInUser() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findByName(username);
        if (user == null)
            throw new UsernameNotFoundException("No logged in user found");
        return user;
    }

}
