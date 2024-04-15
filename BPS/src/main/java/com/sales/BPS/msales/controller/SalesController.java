package com.sales.BPS.msales.controller;

import com.sales.BPS.config.CookieUtil;
import com.sales.BPS.msales.dto.ClientSalesDTO;
import com.sales.BPS.msales.dto.MonthlySalesDTO;
import com.sales.BPS.msales.dto.ProductSalesDTO;
import com.sales.BPS.msales.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales API", description = "매출조회 API입니다.")
public class SalesController {

    private SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/Clientsales")
    @Tag(name = "Sales API")
    @Operation(summary = "거래처별 매출조회", description = "거래처별 매출, 이익 등 조회합니다.")
    public ResponseEntity<List<ClientSalesDTO>> ClientSales(@RequestParam int year, @RequestParam int month, HttpServletRequest request) {
        List<ClientSalesDTO> clientSalesDTOList = salesService.getAllSalesData(year, month);

        // 쿠키에서 권한 코드 가져오기
        String authCode = CookieUtil.getCookieValue(request, "empAuthCode");

        // 팀장 권한 이상이 아닌 경우 단가, 판매원가, 매출이익, 이익율을 null로 설정
        if (!Arrays.asList("AUTH001", "AUTH002", "AUTH003").contains(authCode)) {
            clientSalesDTOList.forEach(clientSalesDTO -> {
                clientSalesDTO.setProUnit(null);
                clientSalesDTO.setCostOfSales(null);
                clientSalesDTO.setGrossProfit(null);
                clientSalesDTO.setProfitMargin(null);
            });
        }

        return ResponseEntity.ok(clientSalesDTOList);
    }

    @GetMapping("/productsales")
    @Tag(name = "Sales API")
    @Operation(summary = "상품별 매출조회", description = "상품별 매출, 이익 등 조회합니다.")
    public ResponseEntity<List<ProductSalesDTO>> ProductSales(@RequestParam int year, @RequestParam int month, HttpServletRequest request) {
        List<ProductSalesDTO> productSalesDTOS = salesService.aggregateSalesByProduct(year, month);

        // 쿠키에서 권한 코드 가져오기
        String authCode = CookieUtil.getCookieValue(request, "empAuthCode");

        // 팀장 권한 이상이 아닌 경우 단가, 판매원가, 매출이익, 이익율을 null로 설정
        if (!Arrays.asList("AUTH001", "AUTH002", "AUTH003").contains(authCode)) {
            productSalesDTOS.forEach(productSalesDTO -> {
                productSalesDTO.setProUnit(null);
                productSalesDTO.setGrossProfit(null);
                productSalesDTO.setProfitMargin(null);
            });
        }

        return ResponseEntity.ok(productSalesDTOS);
    }

    @GetMapping("/monthlySales")
    public ResponseEntity<List<MonthlySalesDTO>> getMonthlySales(@RequestParam int year) {
        List<MonthlySalesDTO> monthlySales = salesService.getMonthlySales(year);
        return ResponseEntity.ok(monthlySales);
    }

    // My Sales 내 매출 보기 기능을 위한 메서드
    @GetMapping("/employeeSales")
    @Tag(name = "Sales API")
    @Operation(summary = "내 매출 현황", description = "로그인한 사원의 담당 거래처 최근 3개월 매출을 조회합니다.")
    public ResponseEntity<List<ClientSalesDTO>> getEmployeeSales(
            @RequestParam Integer empCode,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        if (year == null || month == null) {
            LocalDate currentDate = LocalDate.now();
            year = currentDate.getYear();
            month = currentDate.getMonthValue();
        }
        List<ClientSalesDTO> employeeSalesData = salesService.getEmployeeSalesData(empCode, year, month);
        return ResponseEntity.ok(employeeSalesData);
    }
}