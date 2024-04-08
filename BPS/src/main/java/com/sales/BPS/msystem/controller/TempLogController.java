package com.sales.BPS.msystem.controller;

import com.sales.BPS.msystem.dto.TempLogDTO;
import com.sales.BPS.msystem.entity.TempLog;
import com.sales.BPS.msystem.repository.TempLogRepository;
import com.sales.BPS.msystem.service.ColdchainService;
import com.sales.BPS.msystem.service.TempLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/temp-logs")
public class TempLogController {

    @Autowired
    private TempLogService tempLogService;
    @Autowired
    private ColdchainService coldchainService;

    @GetMapping("/all")
    public List<TempLogDTO> getAllTempLogsForFrontend() {
        return tempLogService.getAllTempLogsForFrontend();
    }

    @GetMapping("/all-random-temps")
    public List<TempLog> getAllRandomTemperatures() {
        return tempLogService.getAllRandomTempLogs();
    }


    @GetMapping
    public Map<String, Integer> getLatestRandomTemperatures() {
        return coldchainService.getLatestTemperatures();
    }

}
