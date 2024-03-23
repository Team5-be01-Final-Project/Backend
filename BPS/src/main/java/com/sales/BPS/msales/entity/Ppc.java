package com.sales.BPS.msales.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sales.BPS.mproduct.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@IdClass(PpcPK.class)
@Table(name = "PPC")
public class Ppc {

    @Id
    @Column(name = "client_code")
    private String clientCode;

    @Id
    @Column(name = "pro_code")
    private Integer proCode;

    @ManyToOne
    @JoinColumn(name = "client_code", insertable = false, updatable = false)
    @JsonIgnore
    private Client client;

    @ManyToOne
    @JoinColumn(name = "pro_code", insertable = false, updatable = false)
    private Product product;

    @Column(name = "ppc_sale")
    private Integer ppcSale;

    @Transient
    private String clientName;
}
