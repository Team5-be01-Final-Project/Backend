package com.sales.BPS.mproduct.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class VoucherPK implements Serializable {

    private Long voucId;
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherPK voucherPK = (VoucherPK) o;
        return Objects.equals(voucId, voucherPK.voucId) && Objects.equals(product, voucherPK.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucId, product);
    }

}