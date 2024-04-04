package com.sales.BPS.mproduct.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherDto {
    private Long voucId; // 전표번호
    private LocalDate voucDate; // 등록일
    private Integer empCode; // 담당자(emp_code)
    //    private String storageCar; // 담당자의 차량 번호 추가
    private Integer signerCode; // 결재자(emp_code_sign)
    private String clientCode; // 거래처명
//    private LocalDate voucApproval; // 결재일
    private String approvalCode; // 승인상태(코드이름)
    //    private boolean showApproveButton;
//    private boolean showRejectButton;
//    private String voucNote; // 비고

    private Integer proCode; // 제품명
    private Integer voucSale; // 판매가
    private Integer voucAmount; // 수량
    private Long voucSales; // 매출액
}