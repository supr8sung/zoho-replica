package com.xebia.fs101.zohoreplica.service;

import org.springframework.stereotype.Service;
@Service
public interface MailService {


    void sendMail(String toEmail, String body);

}
