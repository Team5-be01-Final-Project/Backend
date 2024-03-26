package com.sales.BPS.mproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Table(name = "APPROVALCODE")
public class ApprovalCode {

    @Id
    @Column(name = "app_code")
    private String appCode;

    @Column(name = "app_name")
    private String appName;

    @OneToMany(mappedBy = "approvalCode")
    private List<Voucher> vouchers = new ArrayList<>();

}