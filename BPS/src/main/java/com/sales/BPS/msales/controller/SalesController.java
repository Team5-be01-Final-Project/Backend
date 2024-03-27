package com.sales.BPS.msales.controller;

import com.sales.BPS.msales.dto.ClientSalesDTO;
import com.sales.BPS.msales.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class SalesController {

    private SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("Clientsales")
    public List<ClientSalesDTO> ClientSales(){
        return salesService.getAllSalesData();
    }
}