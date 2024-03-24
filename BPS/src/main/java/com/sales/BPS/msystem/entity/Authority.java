package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "AUTHORITY")
@IdClass(AuthorityPK.class)
public class Authority {

    @Id
    @Column(name = "emp_code")
    private Integer empCode; // 직접 선언

    @Id
    @Column(name = "auth_code")
    private String authCode; // 직접 선언

    @OneToOne
    @JoinColumn(name = "emp_code", insertable = false, updatable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "auth_code", insertable = false, updatable = false)
    private AuthorityCode authorityCode;
}

