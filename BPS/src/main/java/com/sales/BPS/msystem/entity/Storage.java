package com.sales.BPS.msystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
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

}
