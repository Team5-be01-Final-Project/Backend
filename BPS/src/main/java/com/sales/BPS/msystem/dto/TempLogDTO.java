package com.sales.BPS.msystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TempLogDTO {
    private String tempCode;
    private String storageSeg;
    private String storageCar;
    private Integer tempTemp;
    private String tempDate;
    private String empName;
}