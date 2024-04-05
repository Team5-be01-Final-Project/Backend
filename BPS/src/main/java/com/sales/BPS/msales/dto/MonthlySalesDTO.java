package com.sales.BPS.msales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalesDTO {
    private YearMonth yearMonth; // 년월
    private Long voucSales; // 매출액
    private Long totalGrossProfit; // 매출이익
}
