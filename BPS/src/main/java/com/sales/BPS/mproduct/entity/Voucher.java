package com.sales.BPS.mproduct.entity;

import com.sales.BPS.msales.entity.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;


@Data
@Entity
@IdClass(VoucherPK.class)
public class Voucher {
    @Id
    @Column(name = "vouc_id")
    private Long voucId;

    @Id
    @Column(name = "pro_code")
    private Integer proCode;

    @ManyToOne(fetch = LAZY)
    @MapsId
    @JoinColumn(name = "pro_code")
    private Product product;

    @Column(name = "vouc_date")
    private LocalDate voucDate;

    @Column(name = "vouc_sale")
    private Integer voucSale;

    @Column(name = "vouc_amount")
    private Integer voucAmount;

    @Column(name = "vouc_sales")
    private Integer voucSales;

    @Column(name = "vouc_approval")
    private LocalDate voucApproval;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_code")
    private Client client;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "app_code")
    private ApprovalCode approvalCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emp_code")
    private Employee employee;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emp_code")
    private Employee employeeSign;

    @Column(name = "vouc_note")
    private String voucNote;



}
