package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import com.xebia.fs101.zohoreplica.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new CustomUserDetails(user);
    }
}
