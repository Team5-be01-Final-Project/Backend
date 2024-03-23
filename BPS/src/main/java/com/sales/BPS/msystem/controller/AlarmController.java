package com.sales.BPS.msystem.controller;

import lombok.extern.slf4j.Slf4j;
import com.sales.BPS.msystem.dto.AlarmRequsetDTO;
import com.sales.BPS.msystem.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

@Slf4j
@RestController
@RequestMapping("/alarms")
public class AlarmController {
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

//    @PostMapping("/update")
//    public ResponseEntity<Void> updateAlarmSetting(@RequestParam Integer empCode,
//                                                   @RequestParam String alarmCode,
//                                                   @RequestParam boolean alarmSetting) {
//        alarmService.updateAlarmSetting(empCode, alarmCode, alarmSetting);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateAlarmSetting(@RequestBody AlarmRequsetDTO request) {
        log.info("Received empCode: {}, alarmCode: {}, alarmSetting: {}", request.getEmpCode(), request.getAlarmCode(), request.getAlarmSetting());
        alarmService.updateAlarmSetting(request.getEmpCode(), request.getAlarmCode(), request.getAlarmSetting());
        return ResponseEntity.ok().build();
    }
}
