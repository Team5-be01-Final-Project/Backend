package com.sales.BPS.msales.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientSalesDTO {
    private String ClientName; //거래처 이름
    private String proName; //상품이름
    private Integer proUnit; //단가
    private Integer voucSale; //판매가
    private Integer voucAmount; //수량
    private Integer costOfSales; // 판매 원가 (단가 * 수량)
    private Long voucSales; // 매출액 (판매가 * 수량)
    private Long grossProfit; // 매출이익 (매출액 - 매출원가)
    private Double profitMargin; // 이익률 (매출이익 / 매출액 * 100)
    private LocalDate voucApproval;

}
