package com.example.javastreams.repository.mapper;

import com.example.javastreams.domain.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("employee_id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setSalary(new BigDecimal(rs.getString("salary")));
        employee.setJobId(rs.getString("job_id"));
        // to May 18, 1995
        employee.setHireDate(rs.getDate("hire_date").toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        employee.setDepartmentId(rs.getInt("department_id"));
        if(Long.valueOf(rs.getLong("manager_id")) != null)
            employee.setManagerId(rs.getInt("manager_id"));
        else
            employee.setManagerId(0);
        return employee;
    }
}
