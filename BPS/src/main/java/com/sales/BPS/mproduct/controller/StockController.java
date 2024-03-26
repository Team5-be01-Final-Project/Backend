package com.sales.BPS.mproduct.controller;

import com.sales.BPS.mproduct.dto.StockListDTO;
import com.sales.BPS.mproduct.dto.StockRegisterDTO;
import com.sales.BPS.mproduct.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public ResponseEntity<String> registerStock(@RequestBody StockRegisterDTO stockRegisterDTO) {
        try {
            stockService.registerStock(stockRegisterDTO);
            return ResponseEntity.ok("재고 등록이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("재고 등록 중 오류가 발생했습니다.");
        }
    }


}
