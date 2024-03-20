package com.sales.BPS.msystem.entitiy;

import jakarta.persistence.*;
import lombok.Data;

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

}

