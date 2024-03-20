package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "AUTHORITYCODE")
public class AuthorityCode {

    @Id
    @Column(name = "auth_code")
    private String authCode;

    @Column(name = "auth_name")
    private String authName;

    @OneToOne(mappedBy = "authoritycode")
    private Authority authority;

}

