package com.sales.BPS.msystem.entitiy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "dept_name")
    private String deptName;

}
