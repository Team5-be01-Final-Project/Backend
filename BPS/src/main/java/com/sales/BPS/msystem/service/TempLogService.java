package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.dto.TempLogDTO;
import com.sales.BPS.msystem.entity.TempLog;
import com.sales.BPS.msystem.repository.TempLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempLogService {

    @Autowired
    private TempLogRepository tempLogRepository;

    public List<TempLogDTO> getAllTempLogsForFrontend() {
        List<TempLog> tempLogs = tempLogRepository.findAll();
        return tempLogs.stream().map(tempLog -> {
            TempLogDTO dto = new TempLogDTO();
            dto.setTempCode(tempLog.getTempCode());
            dto.setTempTemp(tempLog.getTempTemp());
            dto.setTempDate(tempLog.getTempDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            // Storage와 Employee 엔티티에서 추가 정보 추출
            if (tempLog.getStorage() != null) {
                dto.setStorageSeg(tempLog.getStorage().getStorageSeg());
                dto.setStorageCar(tempLog.getStorage().getStorageCar());
            }
            if (tempLog.getStorage() != null && tempLog.getStorage().getEmployee() != null) {
                dto.setEmpName(tempLog.getStorage().getEmployee().getEmpName());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public List<TempLog> getAllRandomTempLogs() {
        return tempLogRepository.findAll();
    }

}