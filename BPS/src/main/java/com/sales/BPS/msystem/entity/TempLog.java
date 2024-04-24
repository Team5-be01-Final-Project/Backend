package com.sales.BPS.msystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@IdClass(TempLogPK.class)
@Table(name = "TEMPLOG")
public class TempLog {

    @Id
    @Column(name = "temp_code")
    private String tempCode;

    @Id
    @Column(name = "storage_code")
    private String storageCode; // 직접 선언

    @JsonBackReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "storage_code", insertable = false, updatable = false)
    private Storage storage;

    @Column(name = "temp_temp")
    private Integer tempTemp;

    @Column(name = "temp_date")
    private LocalDateTime tempDate;
}
