package com.xebia.fs101.zohoreplica.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${email.subject}")
    protected String subject;

    @Value("${spring.mail.username}")
    protected String fromEmail;
}
