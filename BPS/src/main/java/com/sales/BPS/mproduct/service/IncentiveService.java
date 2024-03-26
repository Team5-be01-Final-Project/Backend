package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.IncentiveDTO;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncentiveService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private EmployeeRepository employeeRepository;



}
