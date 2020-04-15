package com.xebia.fs101.zohoreplica.model;

public enum UserDesignation {
    CONSULTANT(0), PRINCIPAL_CONSULTANT(1), DIRECTOR(2);

    private final int value;
    UserDesignation(int val) {
        value=val;

    }

    public int getValue(){
        return value;
    }
    public String getDesignation() {

        return this.name();
    }
}
