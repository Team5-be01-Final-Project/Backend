package com.sales.BPS.mproduct.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoucherPK implements Serializable {

    private Long voucId;
    private Integer product;
}

}
