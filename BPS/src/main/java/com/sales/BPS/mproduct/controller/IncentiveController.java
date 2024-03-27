package com.sales.BPS.mproduct.controller;


import com.sales.BPS.mproduct.dto.IncentiveDTO;
import com.sales.BPS.mproduct.service.IncentiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/incentive")
public class IncentiveController {

    @Autowired
    private IncentiveService incentiveService;

    @GetMapping("/list")
    public List<IncentiveDTO> getSalesPerformance(@RequestParam int month) {
        return incentiveService.calculateIncentive(month);
    }
}
