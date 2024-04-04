package com.sales.BPS.mproduct.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoucherPK implements Serializable {

    private Long voucId;
    private Integer proCode;
    public VoucherPK() {
        // Default constructor
    }

    public VoucherPK(Long voucId, Integer proCode) {
        this.voucId = voucId;
        this.proCode = proCode;
    }

    // Getters and setters


}