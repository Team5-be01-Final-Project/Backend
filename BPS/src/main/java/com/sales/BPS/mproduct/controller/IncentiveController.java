package com.sales.BPS.mproduct.controller;


import com.sales.BPS.mproduct.dto.IncentiveDTO;
import com.sales.BPS.mproduct.service.IncentiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/incentive")
@Tag(name = "Incentive API" , description = "인센티브 API입니다.")
public class IncentiveController {

    @Autowired
    private IncentiveService incentiveService;

    @GetMapping("/list")
    @Tag(name = "Incentive API")
    @Operation(summary = "인센티브 조회" ,description = "각 직원의 인센티브를 조회합니다.")
    public List<IncentiveDTO> getSalesPerformance(@RequestParam int year, @RequestParam int month) {
        return incentiveService.calculateIncentive(year, month);
    }

    // 로그인한 사원의 인센티브 조회
    @GetMapping("/myIncentive")
    @Tag(name = "Incentive API")
    @Operation(summary = "내 인센티브 조회" ,description = "로그인한 사원의 인센티브를 조회합니다.")
    public IncentiveDTO getMyIncentive(@RequestParam int year, @RequestParam int month, @RequestParam int empCode) {
        return incentiveService.calculateMyIncentive(year, month, empCode);
    }

    // 인센티브 시뮬레이션
    @GetMapping("/simulation")
    @Tag(name = "Incentive API")
    @Operation(summary = "인센티브 시뮬레이션" ,description = "현재 매출액과 추가 매출액을 기반으로 인센티브를 시뮬레이션합니다.")
    public IncentiveDTO getIncentiveSimulation(@RequestParam int empCode, @RequestParam long currentSales, @RequestParam long additionalSales) {
        return incentiveService.calculateIncentiveSimulation(empCode, currentSales, additionalSales);
    }

}
