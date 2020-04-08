package com.xebia.fs101.zohoreplica.jobs;

import com.xebia.fs101.zohoreplica.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Configuration
@EnableScheduling
public class JobScheduler {
    @Autowired
    private UserTokenRepository userTokenRepository;

    @Scheduled(cron = "0 */1 * * * *")
    private void deleteExpiredTokens(){

        System.out.println("Running job");
        userTokenRepository.deleteExpiredToken();
    }
}
