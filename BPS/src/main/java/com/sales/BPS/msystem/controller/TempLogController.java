package com.sales.BPS.msystem.controller;

import com.sales.BPS.msystem.dto.TempLogDTO;
import com.sales.BPS.msystem.entity.TempLog;
import com.sales.BPS.msystem.repository.TempLogRepository;
import com.sales.BPS.msystem.service.ColdchainService;
import com.sales.BPS.msystem.service.TempLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/temp-logs")
@Tag(name = "TempLog API", description = "이상온도 API입니다.")
public class TempLogController {

    @Autowired
    private TempLogService tempLogService;
    @Autowired
    private ColdchainService coldchainService;

    @GetMapping("/all")
    @Tag(name = "TempLog API")
    @Operation(summary = "이상온도조회",description = "전체온도 중 이상온도만 조회합니다.")
    public List<TempLogDTO> getAllTempLogsForFrontend() {
        return tempLogService.getAllTempLogsForFrontend();
    }


    @GetMapping
    @Tag(name = "TempLog API")
    @Operation(summary = "전체온도조회",description = "대시보드에 표시하기 위한 전체온도를 조회합니다.")
    public Map<String, Integer> getLatestRandomTemperatures() {
        return coldchainService.getLatestTemperatures();
    }

}
