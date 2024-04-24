package com.sales.BPS.msales.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PpcVoucherDTO {
    private Integer proCode; // 상품 코드
    private String proName; // 상품 이름
    private Integer ppcSale; //상품 가격
    private Integer ppcStock;  //상품 재고
}
