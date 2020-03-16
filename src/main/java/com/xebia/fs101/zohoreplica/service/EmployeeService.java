package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.execption.UserNotFoundException;
import com.xebia.fs101.zohoreplica.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee){

        return employeeRepository.save(employee);
    }

    public Employee findById(UUID id){
        return employeeRepository.findById(id).orElseThrow(()->new UserNotFoundException("User id is not valid"));
    }

    public Employee findByName(String username) {
        return employeeRepository.findByUsername(username);
    }
}
