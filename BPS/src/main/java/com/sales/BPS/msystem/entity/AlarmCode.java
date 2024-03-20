package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AlarmCode {
    @Id
    @Column(name = "alarm_code")
    private String alarmCode;

    @Column(name = "alarm_name")
    private String alarmName;

    @OneToOne(mappedBy = "alarmcode")
    @JoinColumn(name = "alarm_code")
    private Alarm alarm;
}
