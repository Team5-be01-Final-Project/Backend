package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.entity.TempLog;
import com.sales.BPS.msystem.repository.TempLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempLogService {

    @Autowired
    private TempLogRepository tempLogRepository;

    public List<TempLog> getAllTempLogs() {
        return tempLogRepository.findAll();
    }

    public List<TempLog> getAllRandomTempLogs() {
        return tempLogRepository.findAll();
    }

}
