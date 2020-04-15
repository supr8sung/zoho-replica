package com.xebia.fs101.zohoreplica.controller;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.xebia.fs101.zohoreplica.api.request.ChangePasswordRequest;
import com.xebia.fs101.zohoreplica.api.request.ForgotPasswordRequest;
import com.xebia.fs101.zohoreplica.api.request.UserManagerRequest;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.FollowResponse;
import com.xebia.fs101.zohoreplica.api.response.GenericResponse;
import com.xebia.fs101.zohoreplica.api.response.UserSearchResponse;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.model.Birthday;
import com.xebia.fs101.zohoreplica.security.AdminOnly;
import com.xebia.fs101.zohoreplica.service.AttendanceService;
import com.xebia.fs101.zohoreplica.service.MailService;
import com.xebia.fs101.zohoreplica.service.UserService;
import com.xebia.fs101.zohoreplica.service.UserTokenService;
import com.xebia.fs101.zohoreplica.utility.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.security.Principal;
import java.util.List;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_SUCESS;
import static com.xebia.fs101.zohoreplica.utility.OtpUtility.generateOtp;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@EnableEncryptableProperties
@RequestMapping(value = "/zoho")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserTokenService userTokenService;

    @GetMapping("/")
    public String root() {

        return "index";
    }

     @AdminOnly
    @PostMapping("/user/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserRequest userRequest, Principal principal) {

        User savedUser = userService.add(userRequest,principal.getName());
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "User saved successfully",
                                                      savedUser.getId());
        return new ResponseEntity<>(genericResponse, CREATED);
    }

    @AdminOnly
    @DeleteMapping("user/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

        userService.deleteUser(id);
        GenericResponse genericResponse = getResponse(TXN_SUCESS,
                                                      "User deleted successfully", "");
        return new ResponseEntity<>(genericResponse, NO_CONTENT);
    }

    @AdminOnly
    @GetMapping("/user/valid/{username")
    public ResponseEntity<?> validateUsername(@PathVariable(value = "username") String username) {

        Boolean isUsernameValid = userService.validateUsername(username);
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "", isUsernameValid);
        return new ResponseEntity<>(genericResponse, OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> view(@PathVariable(value = "username") String username) {

        User requestedUser = userService.findByName(username);
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "",
                                                      requestedUser.toUserViewResponse());
        return new ResponseEntity<>(genericResponse, OK);
    }

    @GetMapping("/user/view/{id}")
    public ResponseEntity<?> viewUser(@PathVariable(value = "id") Long id) {

        User user = userService.findById(id);
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "",
                                                      user.toUserViewResponse());
        return new ResponseEntity<>(genericResponse, OK);
    }

    @GetMapping("/user/{username}/info")
    public ResponseEntity<?> info(@PathVariable(value = "username") String username) {

        User user = userService.findByName(username);
        FollowResponse followResponse = new FollowResponse(user.getFollowersCount(),
                                                           user.getFollowingCount());
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "", followResponse);
        return new ResponseEntity<>(genericResponse, OK);
    }

    @PutMapping("/user/edit")
    public ResponseEntity<?> edit(@Valid @RequestBody UserRequest userRequest,Principal principal) {


        userService.add(userRequest,principal.getName());
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "", "");
        return new ResponseEntity<>(genericResponse, CREATED);
    }

    @PostMapping("/user/photo/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {

        User user = getLoggedInUser();
        User savedUser = userService.uploadPhoto(user, file);
        GenericResponse genericResponse = getResponse(TXN_SUCESS,
                                                      "profile picture uploaded",
                                                      savedUser.getPhoto());
        return new ResponseEntity<>(genericResponse, CREATED);
    }

    @PostMapping("/user/photo/delete")
    public ResponseEntity<?> deletePhoto() {

        User user = getLoggedInUser();
        userService.deletePhoto(user);
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "profile picture deleted",
                                                      "");
        return new ResponseEntity<>(genericResponse, OK);
    }

    @PostMapping("/user/follow/{target}")
    public ResponseEntity<?> follow(@PathVariable(value = "target") Object target) {

        User requestUser = getLoggedInUser();
        User targetUser = userService.findByName(target.toString());
        String message = userService.follow(requestUser, targetUser);
        GenericResponse genericResponse = getResponse(TXN_SUCESS, message, "");
        return new ResponseEntity<>(genericResponse, CREATED);
    }

    @PostMapping("/user/unfollow/{target}")
    public ResponseEntity<?> unfollow(@PathVariable(value = "target") Object target) {

        User requestUser = getLoggedInUser();
        User targetUser = userService.findByName(target.toString());
        String message = userService.unfollow(requestUser, targetUser);
        GenericResponse genericResponse = getResponse(TXN_SUCESS, message, "");
        return new ResponseEntity<>(genericResponse, CREATED);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody ChangePasswordRequest request) {

        User user = userService.findByName(getLoggedInUser().getUsername(),
                                           request.getOldPassword());
        User savedUser = userService.changePassword(user, request.getNewPassword());
        GenericResponse genericResponse = getResponse(TXN_SUCESS,
                                                      "Password changes successfully",
                                                      savedUser.getUsername());
        return new ResponseEntity<>(genericResponse, CREATED);
    }

    @GetMapping("/user/send-otp")
    public ResponseEntity<?> forgotPassword() {

        User user = getLoggedInUser();
        String otp = generateOtp();
        String mailBody = StringUtility.generateOtpBody(otp, user.getFullname());
        mailService.sendMail(user.getEmail(), mailBody);
        long tokenId = userTokenService.save(user.getUsername(), otp);
        GenericResponse genericResponse = getResponse(TXN_SUCESS,
                                                      "OTP sent to your registered mail" +
                                                                      " id", tokenId);
        return new ResponseEntity<>(genericResponse, OK);
    }

    @PostMapping("/user/recover-password")
    public ResponseEntity<?> recoverPassword(@Valid @RequestBody ForgotPasswordRequest request) {

        User user = getLoggedInUser();
        if (!userTokenService.validateOtp(user.getUsername(), request))
            throw new IllegalArgumentException("Invalid OTP");
        userService.changePassword(user, request.getNewPassword());
        GenericResponse genericResponse = getResponse(TXN_SUCESS,
                                                      "Password Changes Successfully",
                                                      "");
        return new ResponseEntity<>(genericResponse, CREATED);
    }

    @GetMapping("/user/search/{keyword}")
    public ResponseEntity<?> search(@PathVariable(value = "keyword") String keyword) {

        List<UserSearchResponse> userSearchResponseList = null;
        try {
            userSearchResponseList = userService.searchByName(keyword);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "",
                                                      userSearchResponseList);
        return new ResponseEntity<>(genericResponse, OK);
    }

    @GetMapping("/user/birthdays")
    public ResponseEntity<?> birthdays() {

        List<Birthday> allBirthdays = userService.allBirthdays();
        GenericResponse genericResponse = getResponse(TXN_SUCESS, "", allBirthdays);
        return new ResponseEntity<>(genericResponse, OK);
    }


    @GetMapping("/user/all-reporting/{id}")
    public ResponseEntity<?> allReporting(@PathVariable(value = "id") Long id){

        GenericResponse genericResponse=getResponse( TXN_SUCESS,"",userService.allReportings(id));
        return new ResponseEntity<>(genericResponse,OK);
    }


    @AdminOnly
    @PostMapping("/user/hierarchy")
    public ResponseEntity<?> userHierarchy(@Valid @RequestBody UserManagerRequest request){

        User user = userService.addReportingManager(request.getUserId(),request.getManagerId() );
        GenericResponse genericResponse=getResponse(TXN_SUCESS,"",user);
        return new ResponseEntity<>(genericResponse,OK);
    }

    private GenericResponse getResponse(String status, String message, Object data) {

        return new GenericResponse.Builder()
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
