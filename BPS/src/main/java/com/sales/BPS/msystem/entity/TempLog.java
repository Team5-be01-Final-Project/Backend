package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@IdClass(TempLogPK.class)
public class TempLog {

    @Id
    @Column(name = "temp_log")
    private String tempLog;

    @Id
    @Column(name = "storage_code")
    private String storageCode;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "storage_code")
    private Storage storage;

    @Column(name = "temp_temp")
    private Integer tempTemp;

    @Column(name = "temp_date")
    private LocalDateTime tempDate;



}
