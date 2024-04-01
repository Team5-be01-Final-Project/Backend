package com.sales.BPS.mproduct.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoucherApprovalDTO {
    @NotNull
    private Integer proCode;
    @NotNull
    private Integer empCodeSign;
}