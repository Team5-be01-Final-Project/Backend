package com.sales.BPS.msales.entity;

import com.sales.BPS.msystem.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class Client {

    @Id
    @Column(name = "client_code")
    private String clientCode;

    @Column(name= "client_name")
    private String clientName;

    @Column(name = "client_class")
    private Integer clientClass;

    @Column(name="client_boss")
    private String clientBoss;

    @Column(name="client_where")
    private String clientWhere;

    @Column(name="client_post")
    private String clientPost;

    @Column(name="client_emp")
    private String clientEmp;

    @Column(name="client_emptel")
    private String clientEmpTel;

    @Column(name="client_start")
    private LocalDate clientStart;

    @Column(name = "client_end")
    private LocalDate clientEnd;

    @Column(name = "client_note")
    private String clientNote;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emp_code")
    private Employee employee;

    @OneToMany(mappedBy = "client")
    private List<Ppc> ppcs = new ArrayList<>();




}
