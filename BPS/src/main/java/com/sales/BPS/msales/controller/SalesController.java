package com.sales.BPS.msales.controller;

import com.sales.BPS.msales.dto.ClientSalesDTO;
import com.sales.BPS.msales.dto.ProductSalesDTO;
import com.sales.BPS.msales.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/Clientsales")
    public ResponseEntity<List<ClientSalesDTO>> ClientSales(){
        List<ClientSalesDTO> clientSalesDTOList = salesService.getAllSalesData();
        return ResponseEntity.ok(clientSalesDTOList);
    }

    @GetMapping("/productsales")
    public ResponseEntity<List<ProductSalesDTO>> ProductSales(){
        List<ProductSalesDTO> productSalesDTOS = salesService.aggregateSalesByProduct();
        return ResponseEntity.ok(productSalesDTOS);
    }
}