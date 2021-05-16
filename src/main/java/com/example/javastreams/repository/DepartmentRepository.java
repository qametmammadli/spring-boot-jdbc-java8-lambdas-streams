package com.example.javastreams.repository;

import com.example.javastreams.domain.Department;
import com.example.javastreams.repository.mapper.DepartmentRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DepartmentRowMapper rowMapper;

    public DepartmentRepository(JdbcTemplate jdbcTemplate, DepartmentRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public List<Department> getDepartments(){
        if(Department.departmentList == null) {
            String sql = "select department_id, department_name, manager_id " +
                    " from departments " +
                    " where manager_id is not null";
            Department.departmentList = jdbcTemplate.query(sql, rowMapper);
        }

        return Department.departmentList;
    }
}
