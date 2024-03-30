package com.sales.BPS.mproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @Column(name = "pro_code") //Product_Code
    private Integer proCode;

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
    private Integer proUnit;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Voucher> vouchers = new ArrayList<>();


    // proCode를 매개변수로 받는 생성자 추가
    public Product(Integer proCode) {
        this.proCode = proCode;
        // 필요한 경우, 다른 필드들도 초기화할 수 있습니다.
    }

}
