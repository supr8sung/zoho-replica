package com.xebia.fs101.zohoreplica.api.response;

public class UserViewResponse {

    private String fullname;
    private String email;
    private String company;
    private String mobile;

    public UserViewResponse(String fullname, String email, String company, String mobile) {
        this.fullname = fullname;
        this.email = email;
        this.company = company;
        this.mobile = mobile;
    }

    private UserViewResponse(Builder builder) {

        fullname = builder.fullname;
        email = builder.email;
        company = builder.company;
        mobile = builder.mobile;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getMobile() {
        return mobile;
    }

    public static final class Builder {

        private String fullname;
        private String email;
        private String company;
        private String mobile;

        public Builder() {
        }

        public Builder withFullname(String val) {
            fullname = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withCompany(String val) {
            company = val;
            return this;
        }

        public Builder withMobile(String val) {
            mobile = val;
            return this;
        }

        public UserViewResponse build() {
            return new UserViewResponse(this);
        }
    }
}
