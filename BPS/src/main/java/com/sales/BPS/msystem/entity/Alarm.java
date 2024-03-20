package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(AlarmPK.class)
public class Alarm {
    @Id
    @Column(name = "emp_code")
    private Integer empCode; // 직접 선언

    @Id
    @Column(name = "alarm_code")
    private String alarmCode; // 직접 선언

    @ManyToOne
    @JoinColumn(name = "emp_code", insertable = false, updatable = false)
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "alarm_code", insertable = false, updatable = false)
    private AlarmCode alarmcode;

    @Column(name = "alarm_settings")
    private boolean alarmSettings;


}
