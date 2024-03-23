package com.sales.BPS.msystem.dto;

import lombok.Data;

@Data
public class AlarmRequsetDTO {
    private Integer empCode;
    private String alarmCode;
    private Boolean alarmSetting;
}
