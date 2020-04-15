package com.xebia.fs101.zohoreplica.api.request;

public class UserManagerRequest {
    private Long userId;
    private  Long managerId;

    public UserManagerRequest(Long userId, Long managerId) {

        this.userId = userId;
        this.managerId = managerId;
    }

    public Long getUserId() {

        return userId;
    }

    public Long getManagerId() {

        return managerId;
    }
}
