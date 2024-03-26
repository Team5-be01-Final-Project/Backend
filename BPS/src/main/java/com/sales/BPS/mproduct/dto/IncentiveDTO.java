package com.sales.BPS.mproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class IncentiveDTO {
    private String empName; //직원 이름
    private String deptName; //부서 이름
    private LocalDate voucMonth; //월
    private Integer voucMonthSales; // 월 매출액 합계
    private Integer salesRank; // 판매 순위
    private Integer incentive; // 인센티브
}
