package com.xebia.fs101.zohoreplica.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Target(METHOD)
@Retention(RUNTIME)
@PreAuthorize("hasRole('ROLE_MANAGER')")
public @interface ManagerOnly {
}
