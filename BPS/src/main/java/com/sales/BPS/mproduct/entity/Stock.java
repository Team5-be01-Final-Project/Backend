package com.sales.BPS.mproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Stock {
    @Id
    @Column(name = "pro_code")
    private int proCode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pro_code")
    private Product product;

    @Column(name = "sto_amo")
    private int stoAmo;
}
