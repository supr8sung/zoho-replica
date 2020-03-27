package com.xebia.fs101.zohoreplica.security;

public enum ZohoApplicationRole {
    ADMIN, EMPLOYEE,EMPLOYER;

    public String getRoleName() {

        return "ROLE_" + this.name();
    }
}
