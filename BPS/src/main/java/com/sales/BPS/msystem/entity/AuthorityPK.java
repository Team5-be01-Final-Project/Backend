package com.sales.BPS.msystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorityPK implements Serializable {

    private Integer empCode;

    private String authCode;
}
