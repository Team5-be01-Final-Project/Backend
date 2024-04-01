package com.sales.BPS.mproduct.entity;

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msystem.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;


@Data
@Entity
@IdClass(VoucherPK.class)
@Table(name = "VOUCHER")
public class Voucher {

    @Id
    @Column(name = "vouc_id")
    private Long voucId;

    @Id
    @Column(name = "pro_code")
    private Integer proCode;

    @ManyToOne
    @JoinColumn(name = "pro_code", insertable = false, updatable = false)
    private Product product;


    @Column(name = "vouc_date")
    private LocalDate voucDate;

    @Column(name = "vouc_sale")
    private Integer voucSale;

    @Column(name = "vouc_amount")
    private Integer voucAmount;

    @Column(name = "vouc_sales")
    private Long voucSales;

    @Column(name = "vouc_approval")
    private LocalDate voucApproval;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_code")
    private Client client;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "app_code")
    private ApprovalCode approvalCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emp_code")
    private Employee employee;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emp_code_sign")
    private Employee employeeSign;

    @Column(name = "vouc_note")
    private String voucNote;

}