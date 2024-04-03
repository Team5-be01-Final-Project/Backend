package com.sales.BPS.mproduct.controller;


import com.sales.BPS.mproduct.dto.VoucherRegistrationDTO;
import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.entity.Stock;
import com.sales.BPS.mproduct.repository.ProductRepository;
import com.sales.BPS.mproduct.repository.StockRepository;
import com.sales.BPS.mproduct.service.VoucherRegistrationService;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.List;

@RestController
@RequestMapping("/VoucherRegistration")
public class VoucherRegistrationController {
    @Autowired
    private VoucherRegistrationService voucherRegistrationService;

    @GetMapping("/generate")
    public Long generateVoucherId() {
        return voucherRegistrationService.generateVoucherId();
    }
}
