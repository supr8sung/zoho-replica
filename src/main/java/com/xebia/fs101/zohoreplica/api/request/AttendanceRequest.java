package com.xebia.fs101.zohoreplica.api.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;
public class AttendanceRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private LocalDate date;

    public String getUserName() {
        return userName;
    }

    public LocalDate getDate() {
        return date;
    }

    public AttendanceRequest(@NotBlank String userName, @NotBlank LocalDate date) {
        this.userName = userName;
        this.date = date;
    }
}
