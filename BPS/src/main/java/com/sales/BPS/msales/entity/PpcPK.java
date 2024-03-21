package com.sales.BPS.msales.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class PpcPK implements Serializable {
    private String clientCode;
    private Integer proCode;
}
