package com.sales.BPS.mproduct.controller;

import com.sales.BPS.mproduct.dto.StockListDTO;
import com.sales.BPS.mproduct.dto.StockProductDTO;
import com.sales.BPS.mproduct.dto.StockRegisterDTO;
import com.sales.BPS.mproduct.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock API", description = "재고관리 API입니다.")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/all")
    @Tag(name = "Stock API")
    @Operation(summary = "재고조회",description = "재고를 조회합니다.")
    public List<StockListDTO> findAllStockList() {
        return stockService.findAllStockList();
    }

    @GetMapping("/search")
    @Tag(name = "Stock API")
    @Operation(summary = "상품조회",description = "상품 리스트를 조회합니다.")
    public List<StockListDTO> findStockListByCondition(@RequestParam(required = false) Integer proCode,
                                                       @RequestParam(required = false) String proName) {
        return stockService.findStockListByCondition(proCode, proName);
    }

    @PostMapping("/register")
    @Tag(name = "Stock API")
    @Operation(summary = "재고등록",description = "재고를 등록 합니다.")
    public ResponseEntity<String> registerStock(@RequestBody StockRegisterDTO stockRegisterDTO) {
        try {
            stockService.registerStock(stockRegisterDTO);
            return ResponseEntity.ok("재고 등록이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("재고 등록 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/product/{proCode}")
    @Tag(name = "Stock API")
    @Operation(summary = "재고조회",description = "품목기준코드로 제품을 조회합니다.")
    public ResponseEntity<StockProductDTO> getProductByCode(@PathVariable Integer proCode) {
        StockProductDTO product = stockService.getProductByCode(proCode);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
