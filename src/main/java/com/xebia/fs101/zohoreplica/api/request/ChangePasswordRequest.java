package com.xebia.fs101.zohoreplica.api.request;

import javax.validation.constraints.NotBlank;
public class ChangePasswordRequest {
    private String oldPassword;
    @NotBlank
    private String newPassword;

    public ChangePasswordRequest( String oldPassword, @NotBlank String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
