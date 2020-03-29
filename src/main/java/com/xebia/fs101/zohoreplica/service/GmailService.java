package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.repository.AttendanceRepository;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class GmailService implements MailService {


    private ExecutorService executorService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    private String toEmail;
    @Value("${email.subject}")
    private String subject;

    private String body;

    @Override
    public void sendMail(String toEmail, String body) {

        Runnable runnable = () -> {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(toEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);
            mailMessage.setFrom(fromEmail);
            javaMailSender.send(mailMessage);
        };
        this.executorService = Executors.newSingleThreadExecutor();

        executorService.submit(runnable);

    }

}

