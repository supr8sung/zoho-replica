package com.xebia.fs101.zohoreplica.api.request;

import com.xebia.fs101.zohoreplica.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
public class UserRequest {


    @NotBlank
    private String username;
    @NotBlank
    private String fullname;
    @NotBlank
    private String mobile;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String company;

    public UserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCompany() {
        return company;
    }

    private UserRequest(Builder builder) {
        username = builder.username;
        fullname = builder.fullname;
        mobile = builder.mobile;
        email = builder.email;
        password = builder.password;
        company = builder.company;
    }

    public User toUser() {

        return new User.Builder()
                .withUsername(this.username)
                .withFullname(this.fullname)
                .withEmail(this.email)
                .withMobile(this.mobile)
                .withCompany(this.company.toUpperCase())
                .withPassword(this.password)
                .build();
    }


    public static final class Builder {
        private String username;
        private String fullname;
        private String mobile;
        private String email;
        private String password;
        private String company;

        public Builder() {
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withFullname(String val) {
            fullname = val;
            return this;
        }

        public Builder withMobile(String val) {
            mobile = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public Builder withCompany(String val) {
            company = val;
            return this;
        }

        public UserRequest build() {
            return new UserRequest(this);
        }
    }
}
