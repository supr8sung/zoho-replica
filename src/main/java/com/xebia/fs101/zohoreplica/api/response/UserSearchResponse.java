package com.xebia.fs101.zohoreplica.api.response;

public class UserSearchResponse {

    private Long id;
    private String  username;
    private String fullname;
    private byte[] photo;

    public UserSearchResponse(Long id, String username, String fullname, byte[] photo) {

        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.photo = photo;
    }



    public String getUsername() {

        return username;
    }

    public Long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public byte[] getPhoto() {
        return photo;
    }




}
