package com.sales.BPS.mproduct.controller;

// VoucherController.java

import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/list")
    public List<VoucherDTO> getAllVouchers() {
        return voucherService.getAllVouchersDTO();
    }
}
