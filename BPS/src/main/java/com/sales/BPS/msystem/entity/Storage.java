package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "STORAGE")
public class Storage {

    @Id
    @Column(name = "storage_code")
    private String storageCode;

    @Column(name = "storage_seg")
    private String storageSeg;

    @Column(name = "storage_car")
    private String storageCar;

    @Column(name = "storage_ware")
    private String storageWare;

    @OneToMany(mappedBy = "storage")
    private List<TempLog> tempLogs = new ArrayList<>();

    @OneToOne(mappedBy = "storage")
    private Employee employee;

}
