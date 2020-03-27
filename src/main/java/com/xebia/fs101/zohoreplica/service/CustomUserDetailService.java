package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.repository.EmployeeRepository;
import com.xebia.fs101.zohoreplica.security.core.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);

        System.out.println("employee = " + employee);
//        if(Objects.nonNull(employee))
//            throw new UsernameNotFoundException(String.format("User %s not found", username));
        return new CustomUserDetails(employee);
    }
}
