package com.sales.BPS.msales.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class PpcPK implements Serializable {
    private String clientCode;
    private Integer proCode;

    public PpcPK() {
        // 기본 생성자
    }

    public PpcPK(String clientCode, Integer proCode) {
        this.clientCode = clientCode;
        this.proCode = proCode;
    }
}
