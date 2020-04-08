package com.xebia.fs101.zohoreplica.api.request;

import javax.validation.constraints.NotBlank;

public class ForgotPasswordRequest {
    @NotBlank
    private Long id;
    @NotBlank
    private String otp;
    @NotBlank
    private String newPassword;

    public ForgotPasswordRequest(Long id, String otp, String newPassword) {

        this.id = id;
        this.otp = otp;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {

        return newPassword;
    }

    public String getOtp() {

        return otp;
    }

    public Long getId() {

        return id;
    }
}
