package com.xebia.fs101.zohoreplica.security;

public enum ZohoApplicationRole {
    ADMIN, EMPLOYEE,MANAGER;

    public String getRoleName() {

        return "ROLE_" + this.name();
    }
}
