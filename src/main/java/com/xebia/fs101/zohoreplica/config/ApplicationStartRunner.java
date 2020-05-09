package com.xebia.fs101.zohoreplica.config;

import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import com.xebia.fs101.zohoreplica.security.ZohoApplicationRole;
import com.xebia.fs101.zohoreplica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.xebia.fs101.zohoreplica.model.Clients.XEBIA;

@Component
public class ApplicationStartRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        User admin = userRepository.findByUsername("admin");
        if(admin==null){
            User user=new User.Builder().withRole(ZohoApplicationRole.ADMIN)
                    .withUsername("admin")
                    .withFullname("admin")
                    .withPassword(passwordEncoder.encode("12345"))
                    .withEmail("admin@gmail.com")
                    .withDesignation(2)
                    .withCompany(XEBIA)
                    .build();
            userRepository.save(user);
        }
        userService.calculateBirthdays();

    }
}
