package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.api.request.ForgotPasswordRequest;
import com.xebia.fs101.zohoreplica.entity.UserToken;
import com.xebia.fs101.zohoreplica.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {
    @Autowired
    private UserTokenRepository userTokenRepository;

    public long save(String username, String token) {

        UserToken userToken = userTokenRepository.save(new UserToken(username, token));
        return userToken.getId();
    }

    public boolean validateOtp(String username, ForgotPasswordRequest request) {

        UserToken userToken = userTokenRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("Your OTP is expired try sending it aganin"));
        return userToken.getToken().equals(request.getOtp()) && userToken.getUsername().equals(
                username);
    }
}
