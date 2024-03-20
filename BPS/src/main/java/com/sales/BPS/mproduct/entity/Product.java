package com.sales.BPS.mproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @Column(name = "pro_code") //Product_Code
    private int proCode;

    @Column(name = "pro_name") //Product_name
    private String proName;

    @Column(name = "pro_seg")
    private String proSeg;

    @Column(name = "pro_ingre")
    private String proIngre;

    @Column(name = "pro_atc")
    private String proAtc;

    @Column(name = "pro_cat")
    private String proCat;

    @Column(name = "pro_unit")
    private int proUnit;

}