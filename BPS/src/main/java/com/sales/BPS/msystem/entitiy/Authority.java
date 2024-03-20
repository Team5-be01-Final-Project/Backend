package com.sales.BPS.msystem.entitiy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "AUTHORITY")
public class Authority {

    @Id
    @Column(name = "emp_code")
    private int empCode;

    @Id
    @Column(name = "auth_code")
    private String authCode;

    @ManyToOne
    @JoinColumn(name = "auth_code", referencedColumnName = "auth_code", insertable = false, updatable = false)
    private AuthorityCode authorityCode;

    @ManyToOne
    @JoinColumn(name = "emp_code", referencedColumnName = "emp_code", insertable = false, updatable = false)
    private Employee employee;

}

