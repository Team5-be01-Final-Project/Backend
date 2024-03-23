package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.entity.Alarm;
import com.sales.BPS.msystem.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Transactional
    public void updateAlarmSetting(Integer empCode, String alarmCode, boolean alarmSetting) {
        alarmRepository.findByEmpCodeAndAlarmCode(empCode, alarmCode).ifPresentOrElse(
                alarm -> {
                    // 알람이 존재하는 경우: 알람 설정을 요청받은 값으로 변경
                    alarm.setAlarmSettings(alarmSetting);
                    alarmRepository.save(alarm);
                },
                () -> {
                    //알람이 존재하지 않는 경우, 새로운 Alarm 객체를 생성하여 데이터베이스에 저장
                    Alarm newAlarm = new Alarm();
                    newAlarm.setEmpCode(empCode);
                    newAlarm.setAlarmCode(alarmCode);
                    newAlarm.setAlarmSettings(alarmSetting);
                    alarmRepository.save(newAlarm);
                }
        );
    }
}
