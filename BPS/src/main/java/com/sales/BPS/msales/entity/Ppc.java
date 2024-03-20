package com.sales.BPS.msales.entity;

import com.sales.BPS.mproduct.entity.Product;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class Ppc {

    @Id
    @Column(name = "client_code")
    private String clientCode;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "client_code")
    private Client client;

    @Id
    @Column(name = "pro_code")
    private Integer proCode;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "pro_code")
    private Product product;

    @Column
    private Integer ppcSale;


}
