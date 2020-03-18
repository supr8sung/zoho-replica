package com.xebia.fs101.zohoreplica.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
@Service
public interface MailService {


    void sendMail(String toEmail, String body);

}
