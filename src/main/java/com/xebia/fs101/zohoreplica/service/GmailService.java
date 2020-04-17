package com.xebia.fs101.zohoreplica.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class GmailService extends MailService implements Callable {
    private JavaMailSender javaMailSender;
    private String toEmail;
    private String body;
    private CountDownLatch countDownLatch;

    public GmailService(CountDownLatch countDownLatch, String toEmail, String body) {

        this.countDownLatch = countDownLatch;
        this.body = body;
        this.toEmail = toEmail;
    }

    @Override
    public Object call() {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setFrom(fromEmail);
        javaMailSender.send(mailMessage);
        // Thread.sleep(6000);
        countDownLatch.countDown();
        return "Mail Sent Succesfully";
    }
}

