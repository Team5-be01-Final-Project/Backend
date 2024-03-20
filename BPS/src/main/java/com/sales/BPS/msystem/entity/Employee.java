package com.sales.BPS.msystem.entity;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.msales.entity.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "dept_code")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "pos_code")
    private Position position;

    @OneToOne
    @JoinColumn(name = "storage_code")
    private Storage storage;

    @OneToOne(mappedBy = "employee")
    private Authority authority;

    @OneToMany(mappedBy = "employee")
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    private List<Voucher> vouchers = new ArrayList<>();


}
