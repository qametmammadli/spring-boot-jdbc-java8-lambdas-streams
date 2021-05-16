package com.example.javastreams.repository.mapper;

import com.example.javastreams.domain.Department;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DepartmentRowMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet rs, int i) throws SQLException {
        Department department = new Department();
        department.setId(rs.getLong("department_id"));
        department.setDepartmentName(rs.getString("department_name"));
        department.setManagerId(rs.getInt("manager_id"));
        return department;

    }
}
