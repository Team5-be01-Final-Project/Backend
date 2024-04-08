package com.sales.BPS.msystem.controller;

import com.sales.BPS.msystem.dto.AlarmSettingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import com.sales.BPS.msystem.dto.AlarmRequestDTO;
import com.sales.BPS.msystem.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/alarms")
@Tag(name = "System API", description = "시스템관리 API입니다.")
public class AlarmController {
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }


    @PostMapping("/update")
    @Tag(name = "System API")
    @Operation(summary = "사원정보수정",description = "알림대상자를 수정합니다.")
    public ResponseEntity<Void> updateAlarmSetting(@RequestBody AlarmRequestDTO request) {
        log.info("Received empCode: {}, alarmCode: {}, alarmSetting: {}", request.getEmpCode(), request.getAlarmCode(), request.getAlarmSetting());
        alarmService.updateAlarmSetting(request.getEmpCode(), request.getAlarmCode(), request.getAlarmSetting());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/settings/{empCode}")
    @Tag(name = "System API")
    @Operation(summary = "알림 설정 조회",description = "알림의 종류를 조회합니다.")
    public ResponseEntity<List<AlarmSettingDTO>> getAlarmSettings(@PathVariable Integer empCode) {
        List<AlarmSettingDTO> alarmSettings = alarmService.getAlarmSettings(empCode);
        return ResponseEntity.ok(alarmSettings);
    }
}
