package com.example.javastreams.service;

import com.example.javastreams.domain.Department;
import com.example.javastreams.domain.Employee;
import com.example.javastreams.repository.DepartmentRepository;
import com.example.javastreams.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public StreamService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getEmployees(){
        return employeeRepository.getEmployees();
    }

    public List<Department> getDepartments(){
        return departmentRepository.getDepartments();
    }
}
