package com.xebia.fs101.zohoreplica.security;

public enum ZohoApplicationRole {
    WRITER, EDITOR, ADMIN, USER;

    public String getRoleName() {

        return "ROLE_" + this.name();
    }
}
