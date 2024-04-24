package com.sales.BPS.msales.dto;

import lombok.Data;

import javax.naming.InsufficientResourcesException;

@Data
public class PpcDTO {

        private String clientCode; // 거래처 코드
        private String clientName; // 거래처 이름
        private String ProAtc;
        private Integer proCode; // 상품 코드
        private String proName; // 상품 이름
        private String proSeg; // 상품 분류
        private String proCat;
        private Integer ppcSale;
        private Integer proUnit;


}
