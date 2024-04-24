package com.sales.BPS.msystem.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlarmPK implements Serializable {

    private Integer empCode;

    private String alarmCode;
}
