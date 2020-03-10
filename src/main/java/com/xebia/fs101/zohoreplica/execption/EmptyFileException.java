package com.xebia.fs101.zohoreplica.execption;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException(String message) {
        super(message);
    }
}
