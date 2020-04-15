package com.xebia.fs101.zohoreplica.api.request;

import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.model.Clients;
import com.xebia.fs101.zohoreplica.model.UserCity;
import com.xebia.fs101.zohoreplica.model.UserDesignation;
import com.xebia.fs101.zohoreplica.security.ZohoApplicationRole;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;

import static com.xebia.fs101.zohoreplica.security.ZohoApplicationRole.EMPLOYEE;
import static com.xebia.fs101.zohoreplica.utility.DateUtility.getLocalDate;
import static com.xebia.fs101.zohoreplica.utility.StringUtility.getRole;

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
    private String birthday;
    @NotNull
    private UserDesignation designation;
    @NotNull
    private UserCity city;

    public UserRequest() {

    }

    private UserRequest(Builder builder) {

        username = builder.username;
        fullname = builder.fullname;
        mobile = builder.mobile;
        email = builder.email;
        password = builder.password;
        birthday = builder.birthday;
        designation = builder.designation;
        city = builder.city;
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



    public UserDesignation getDesignation() {

        return designation;
    }

    public String getBirthday() {

        return birthday;
    }

    public UserCity getCity() {

        return city;
    }

    public User toUser(PasswordEncoder passwordEncoder, Clients company) {

        return new User.Builder()
                .withUsername(this.username)
                .withFullname(this.fullname)
                .withEmail(this.email)
                .withMobile(this.mobile)
                .withCompany(company)
                .withPassword(passwordEncoder.encode(this.password))
                .withRole(getRole(this.designation))
                .withFollowing(new HashSet<>())
                .withFollowers(new HashSet<>())
                .withFollowingCount(0)
                .withFollowersCount(0)
                .withBirthDay(getLocalDate(this.birthday))
                .withCity(this.getCity())
                .withDesignation(this.getDesignation().getValue())
                .build();
    }

    public static final class Builder {

        private UserCity city;
        private UserDesignation designation;
        private String birthday;
        private String username;
        private String fullname;
        private String mobile;
        private String email;
        private String password;

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





        public Builder wihtBirthday(String val) {

            birthday = val;
            return this;
        }

        public Builder withCity(UserCity val) {

            city = val;
            return this;
        }

        public Builder withDesignation(UserDesignation val) {

            designation = val;
            return this;
        }

        public UserRequest build() {

            return new UserRequest(this);
        }
    }
}
