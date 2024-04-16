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
@Tag(name = "Stock API", description = "재고 관리 API입니다.")
public class StockController {

    private final StockService stockService;

    // StockService 의존성 주입을 위한 생성자
    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // 모든 재고 목록 조회
    @GetMapping("/all")
    @Tag(name = "Stock API")
    @Operation(summary = "재고 조회", description = "모든 재고를 조회합니다.")
    public List<StockListDTO> findAllStockList() {
        return stockService.findAllStockList();
    }

    // 조건에 따른 재고 목록 조회
    @GetMapping("/search")
    @Tag(name = "Stock API")
    @Operation(summary = "상품 검색", description = "조건에 따라 상품을 검색합니다.")
    public List<StockListDTO> findStockListByCondition(@RequestParam(required = false) Integer proCode,
                                                       @RequestParam(required = false) String proName) {
        return stockService.findStockListByCondition(proCode, proName);
    }

    // 재고 등록
    @PostMapping("/register")
    @Tag(name = "Stock API")
    @Operation(summary = "재고 등록", description = "새로운 재고를 등록합니다.")
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

    // 상품명으로 상품 정보 조회
    @GetMapping("/product/{proName}")
    @Tag(name = "Stock API")
    @Operation(summary = "상품 조회", description = "상품명으로 상품 정보를 조회합니다.")
    public ResponseEntity<StockProductDTO> getProductByName(@PathVariable String proName) {
        StockProductDTO product = stockService.getProductByName(proName);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/decrease") //재고 차감 로직
    @Tag(name = "Stock API")
    @Operation(summary = "재고 차감", description = "전표가 결재되면 재고가 차감됩니다.")
    public ResponseEntity<?> decreaseStock(@RequestBody StockRegisterDTO request) {
        boolean success = stockService.decreaseStock(request.getProCode(), request.getStoAmo());
        if (success) {
            return ResponseEntity.ok().body("재고 차감이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Insufficient stock");
        }
    }
}
