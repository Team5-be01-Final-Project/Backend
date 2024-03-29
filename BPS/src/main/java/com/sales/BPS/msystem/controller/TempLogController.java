package com.sales.BPS.msystem.controller;

import com.sales.BPS.msystem.entity.TempLog;
import com.sales.BPS.msystem.repository.TempLogRepository;
import com.sales.BPS.msystem.service.TempLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/temp-logs")
public class TempLogController {

    @Autowired
    private TempLogService tempLogService;

    @GetMapping("/all")
    public List<TempLog> getAllTempLogs() {
        return tempLogService.getAllTempLogs();
    }

    @GetMapping("/all-random-temps")
    public List<TempLog> getAllRandomTemperatures() {
        return tempLogService.getAllRandomTempLogs();
    }
}
