package com.sales.BPS.mproduct.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
public class ApprovalCode {

    @Id
    @Column(name = "app_code")
    private String appCode;

    @Column(name = "app_name")
    private String appName;

    @OneToOne(mappedBy = "approvalCode")
    private Voucher voucher;

}
