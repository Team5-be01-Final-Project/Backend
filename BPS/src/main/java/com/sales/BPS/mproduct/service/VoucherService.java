package com.sales.BPS.mproduct.service;

// VoucherService.java

import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msystem.entity.Employee;

import com.sales.BPS.msystem.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, EmployeeRepository employeeRepository) {
        this.voucherRepository = voucherRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public List<VoucherDTO> getAllVouchersDTO() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private VoucherDTO mapToDTO(Voucher voucher) {
        Employee employee = voucher.getEmployee();
        String empName = employee != null ? employee.getEmpName() : "";
        String clientName = voucher.getClientName(); // Assuming there's a method to get client name from voucher

        VoucherDTO dto = new VoucherDTO();
        dto.setVoucId(voucher.getVoucId());
        dto.setEmpName(empName);
        dto.setClientName(clientName);
        dto.setVoucDate(voucher.getVoucDate());
        dto.setAppName(voucher.getAppName());
        dto.setVoucApproval(String.valueOf(voucher.getVoucApproval()));
        return dto;
    }
}
