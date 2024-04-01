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
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    @PostMapping
    public ResponseEntity<Long> createVoucher(@RequestBody VoucherRegistrationDTO registrationDTO, HttpServletRequest request) {
        String empCodeValue = WebUtils.getCookie(request, "emp_code").getValue();
        Integer empCode = Integer.parseInt(empCodeValue);
        registrationDTO.setEmpCode(empCode);

        Long voucherId = voucherRegistrationService.createVoucher(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(voucherId);
    }

    @GetMapping("/signers")
    public ResponseEntity<List<Employee>> getSignersWithAuthority() {
        List<Employee> signers = voucherRegistrationService.findSignersWithAuthority();
        return ResponseEntity.ok(signers);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientRepository.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }
}
