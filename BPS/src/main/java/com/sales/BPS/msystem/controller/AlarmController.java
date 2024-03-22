package com.sales.BPS.msystem.controller;

import com.sales.BPS.msystem.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alarms")
public class AlarmController {
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateAlarmSetting(@RequestParam Integer empCode,
                                                   @RequestParam String alarmCode,
                                                   @RequestParam boolean alarmSetting) {
        alarmService.updateAlarmSetting(empCode, alarmCode, alarmSetting);
        return ResponseEntity.ok().build();
    }
}
