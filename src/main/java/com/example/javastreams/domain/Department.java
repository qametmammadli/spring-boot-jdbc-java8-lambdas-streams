package com.example.javastreams.domain;

import java.util.List;

public class Department {
    private long id;
    private long managerId;
    private String departmentName;
    public static List<Department> departmentList;

    public Department() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", department_name='" + departmentName + '\'' +
                ", manager_id=" + managerId +
                '}';
    }
}

