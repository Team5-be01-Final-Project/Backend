package com.sales.BPS.msales.controller;

import com.sales.BPS.msales.dto.ClientSalesDTO;
import com.sales.BPS.msales.dto.MonthlySalesDTO;
import com.sales.BPS.msales.dto.ProductSalesDTO;
import com.sales.BPS.msales.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
@Tag(name = "Sales API", description = "매출조회 API입니다.")
public class SalesController {

    private SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/Clientsales")
    @Tag(name = "Sales API")
    @Operation(summary = "거래처별 매출조회",description = "거래처별 매출, 이익 등 조회합니다.")
    public ResponseEntity<List<ClientSalesDTO>> ClientSales(@RequestParam int year,@RequestParam int month){
        List<ClientSalesDTO> clientSalesDTOList = salesService.getAllSalesData(year,month);
        return ResponseEntity.ok(clientSalesDTOList);
    }

    @GetMapping("/productsales")
    @Tag(name = "Sales API")
    @Operation(summary = "상품별 매출조회",description = "상품별 매출, 이익 등 조회합니다.")
    public ResponseEntity<List<ProductSalesDTO>> ProductSales(@RequestParam int year, @RequestParam int month){
        List<ProductSalesDTO> productSalesDTOS = salesService.aggregateSalesByProduct(year,month);
        return ResponseEntity.ok(productSalesDTOS);
    }

    @GetMapping("/monthlySales")
    public ResponseEntity<List<MonthlySalesDTO>> getMonthlySales(@RequestParam int year) {
        List<MonthlySalesDTO> monthlySales = salesService.getMonthlySales(year);
        return ResponseEntity.ok(monthlySales);
    }
}