package com.sales.BPS.mproduct.dto;

import lombok.Data;

@Data
public class IncentiveDTO {
    private String empName; //직원 이름
    private String deptName; //부서 이름
    private long voucMonthSales; // 월 매출액 합계
    private Integer salesRank; // 판매 순위
    private Integer incentive; // 인센티브
    private String empImg;

    public IncentiveDTO(String empName, String deptName, long voucMonthSales, Integer salesRank, Integer incentive) {
        this.empName = empName;
        this.deptName = deptName.replace("\r","");
        this.voucMonthSales = voucMonthSales;
        this.salesRank = salesRank;
        this.incentive = incentive;
    }

    @Override
    public String toString() {
        return "IncentiveDTO{" +
                "empName='" + empName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", voucMonthSales=" + voucMonthSales +
                ", salesRank=" + salesRank +
                ", incentive=" + incentive +
                '}';
    }
}
