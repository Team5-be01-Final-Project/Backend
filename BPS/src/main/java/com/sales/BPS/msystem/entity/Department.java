package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "dept_name")
    private String deptName;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();


}
