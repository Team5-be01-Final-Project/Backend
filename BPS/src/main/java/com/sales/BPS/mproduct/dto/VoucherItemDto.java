package com.sales.BPS.mproduct.dto;

import lombok.Data;

@Data
public class VoucherItemDto {
    private Integer productId;
    private Integer voucAmount; // 해당 제품의 수량
    private Integer voucSale; // 해당 제품의 거래처별 판매가격
    private Long voucherSales; // 해당 제품의 총 판매액 (수량 * 판매가격)
}

