package com.sales.BPS.msystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmSettingDTO {
    private String alarmCode;
    private Boolean alarmSetting;
}
