package com.sales.BPS.mproduct.controller;

import com.sales.BPS.mproduct.dto.StockListDTO;
import com.sales.BPS.mproduct.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/all")
    public List<StockListDTO> findAllStockList() {
        return stockService.findAllStockList();
    }

    @GetMapping("/search")
    public List<StockListDTO> findStockListByCondition(@RequestParam(required = false) Integer proCode,
                                                       @RequestParam(required = false) String proName) {
        return stockService.findStockListByCondition(proCode, proName);
    }


}
