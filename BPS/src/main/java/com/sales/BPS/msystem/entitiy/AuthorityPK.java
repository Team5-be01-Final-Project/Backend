package com.sales.BPS.msystem.entitiy;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorityPK implements Serializable {

    private Integer empCode;

    private String authCode;
}
