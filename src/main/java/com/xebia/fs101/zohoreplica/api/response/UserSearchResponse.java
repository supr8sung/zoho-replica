package com.xebia.fs101.zohoreplica.api.response;

import java.util.UUID;
public class UserSearchResponse {
    private UUID id;
    private String fullname;

    public UserSearchResponse(UUID id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public UUID getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }
}
