package com.sales.BPS.mproduct.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherDTO {
    private Long voucId; // 전표번호
    private String proName; // 제품명
    private LocalDate voucDate; // 등록일
    private Long voucSale; // 판매가
    private Integer voucAmount; // 수량
    private Integer voucSales; // 매출액
    private LocalDate voucApproval; // 결재일
    private String clientName; // 거래처명
    private String empName; // 담당자(emp_code) 이름
    private String signerName; // 결재자(emp_code_sign) 이름
    private String voucNote; // 비고
    private String approvalStatus; // 승인상태(코드이름)


}
