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
    @Column(name = "temp_code")
    private String tempCode;

    @Id
    @ManyToOne
    @JoinColumn(name = "storage_code")
    @Column(name = "storage_code")
    private Storage storageCode;

    @Column(name = "temp_temp")
    private Integer tempTemp;

    @Column(name = "temp_date")
    private LocalDateTime tempDate;



}
