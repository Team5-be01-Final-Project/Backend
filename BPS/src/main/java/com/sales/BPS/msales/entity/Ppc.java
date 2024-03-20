package com.sales.BPS.msales.entity;

import com.sales.BPS.mproduct.entity.Product;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@IdClass(PpcPK.class)
public class Ppc {

    @Id
    @ManyToOne
    @JoinColumn(name = "client_code")
    private Client client;


    @Id
    @ManyToOne
    @JoinColumn(name = "pro_code")
    private Product product;

    @Column(name = "ppc_sale")
    private Integer ppcSale;


}
