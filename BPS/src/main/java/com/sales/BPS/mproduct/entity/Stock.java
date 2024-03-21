package com.sales.BPS.mproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "STOCK")
public class Stock {
    @Id
    @Column(name = "pro_code")
    private Integer proCode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pro_code")
    private Product product;

    @Column(name = "sto_amo")
    private Integer stoAmo;
}
