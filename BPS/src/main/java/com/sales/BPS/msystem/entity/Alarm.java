package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(AlarmPK.class)
public class Alarm {

    @Id
    @ManyToOne
    @JoinColumn(name = "emp_code")
    private Employee employee;

    @Id
    @OneToOne
    @JoinColumn(name = "alarm_code")
    private AlarmCode alarmcode;

    @Column(name = "alarm_settings")
    private boolean alarmSettings;
}
