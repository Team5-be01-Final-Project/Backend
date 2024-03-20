package com.sales.BPS.msystem.entitiy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "AUTHORITY")
public class Authority {

    @Id
    @Column(name = "emp_code")
    private Integer empCode;

    @Id
    @Column(name = "auth_code")
    private String authCode;

    @MapsId("auth_code")
    @ManyToOne
    @JoinColumn(name = "auth_code")
    private AuthorityCode authorityCode;

    @MapsId("emp_code")
    @ManyToOne
    @JoinColumn(name = "emp_code")
    private Employee employee;

}

