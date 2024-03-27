package com.sales.BPS.mproduct.entity;

import java.io.Serializable;

public class StockPK implements Serializable {
    private Integer proCode;

    public StockPK() {
    }

    public StockPK(Integer proCode) {
        this.proCode = proCode;
    }

}
