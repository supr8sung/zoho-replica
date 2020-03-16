package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface EmployeeRepository extends JpaRepository<Employee,UUID> {
    Employee findByUsername(String username);
}
