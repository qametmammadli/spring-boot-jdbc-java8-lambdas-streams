package com.example.javastreams.repository;

import com.example.javastreams.domain.Employee;
import com.example.javastreams.repository.mapper.EmployeeRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRowMapper rowMapper;

    public EmployeeRepository(JdbcTemplate jdbcTemplate, EmployeeRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public List<Employee> getEmployees(){
        if(Employee.employeeList == null) {
            String sql = "select employee_id, first_name, last_name, salary, " +
                    "job_id, hire_date, department_id, manager_id " +
                    "from employees " +
                    "where department_id is not null";
            Employee.employeeList = jdbcTemplate.query(sql, rowMapper);
        }
        return Employee.employeeList;
    }
}
