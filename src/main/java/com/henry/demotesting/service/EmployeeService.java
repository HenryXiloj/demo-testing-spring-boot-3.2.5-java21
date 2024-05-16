package com.henry.demotesting.service;

import com.henry.demotesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getEmployees();
    Optional<Employee> findById(Long id);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(long id);

}
