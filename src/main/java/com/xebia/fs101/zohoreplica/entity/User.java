package com.xebia.fs101.zohoreplica.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String username;
    @NotBlank
    private String fullname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String mobile;
    @NotBlank
    private String password;
    @NotBlank
    private String company;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getCompany() {
        return company;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    private User(Builder builder) {
        id = builder.id;
        username = builder.username;
        fullname = builder.fullname;
        email = builder.email;
        mobile = builder.mobile;
        password = builder.password;
        company = builder.company;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }


    public static final class Builder {
        private UUID id;
        private @NotBlank String username;
        private @NotBlank String fullname;
        private @NotBlank @Email String email;
        private @NotBlank String mobile;
        private @NotBlank String password;
        private @NotBlank String company;
        private Date createdAt;
        private Date updatedAt;

        public Builder() {
        }

        public Builder withId(UUID val) {
            id = val;
            return this;
        }

        public Builder withUsername(@NotBlank String val) {
            username = val;
            return this;
        }

        public Builder withFullname(@NotBlank String val) {
            fullname = val;
            return this;
        }

        public Builder withEmail(@NotBlank @Email String val) {
            email = val;
            return this;
        }

        public Builder withMobile(@NotBlank String val) {
            mobile = val;
            return this;
        }

        public Builder withPassword(@NotBlank String val) {
            password = val;
            return this;
        }

        public Builder withCompany(@NotBlank String val) {
            company = val;
            return this;
        }

        public Builder withCreatedAt(Date val) {
            createdAt = val;
            return this;
        }

        public Builder withUpdatedAt(Date val) {
            updatedAt = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
