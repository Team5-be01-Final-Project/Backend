package com.sales.BPS.msystem.entity;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.msales.entity.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @Column(name = "emp_code")
    private Integer empCode;

    @Column(name = "emp_pw")
    private String empPw;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "emp_tel")
    private String empTel;

    @Column(name = "emp_email")
    private String empEmail;

    @Column(name = "emp_startdate")
    private LocalDate empStartDate;

    @Column(name = "emp_enddate")
    private LocalDate empEndDate;

    @Column(name = "emp_img")
    private String empImg;

    @ManyToOne
    @JoinColumn(name = "dept_code")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "pos_code")
    private Positions positions;

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
