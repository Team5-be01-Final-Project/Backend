package com.sales.BPS.msystem.entitiy;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @Column(name = "emp_code")
    private int empCode;

    @Column(name = "emp_pw")
    private String empPw;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "emp_tel")
    private String empTel;

    @Column(name = "emp_email")
    private String empEmail;

    @Column(name = "emp_startdate")
    @Temporal(TemporalType.DATE)
    private Date empStartDate;

    @Column(name = "emp_enddate")
    @Temporal(TemporalType.DATE)
    private Date empEndDate;

    @Column(name = "emp_img")
    private String empImg;

    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "pos_code")
    private String posCode;

    @Column(name = "storage_code")
    private String storageCode;

    @ManyToOne
    @JoinColumn(name = "dept_code", referencedColumnName = "dept_code", insertable = false, updatable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "pos_code", referencedColumnName = "pos_code", insertable = false, updatable = false)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "storage_code", referencedColumnName = "storage_code", insertable = false, updatable = false)
    private Storage storage;

}
