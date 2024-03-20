package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "AUTHORITY")
@IdClass(AuthorityPK.class)
public class Authority {

    @Id
    @OneToOne
    @JoinColumn(name = "emp_code")
    private Employee employee;

    @Id
    @OneToOne
    @JoinColumn(name = "auth_code")
    private AuthorityCode authorityCode;

}

