package com.sales.BPS.mproduct.dto;

// VoucherDTO.java

import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherDTO {
    private Long voucId;
    private String empName;
    private String clientName;
    private LocalDate voucDate;
    private String appName;
    private String voucApproval;
}
