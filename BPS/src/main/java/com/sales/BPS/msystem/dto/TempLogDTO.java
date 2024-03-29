package com.sales.BPS.msystem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TempLogDTO {
    private String tempCode;
    private String storageSeg;
    private String storageCar;
    private Integer tempTemp;
    private LocalDate tempDate;
    private String empName;
}