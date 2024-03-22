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
                    //알람이 존재하고, 알람을 비활성화(false)하는 경우에만 알람 설정을 false로 변경
                    if (!alarmSetting && alarm.isAlarmSettings()) {
                        alarm.setAlarmSettings(false);
                        alarmRepository.save(alarm);
                    }
                },
                () -> {
                    //알람이 존재하지 않고, 알람을 활성화(true)하는 경우에는 새로운 Alarm 객체를 생성하여 데이터베이스에 저장
                    if (alarmSetting) {
                        Alarm newAlarm = new Alarm();
                        newAlarm.setEmpCode(empCode);
                        newAlarm.setAlarmCode(alarmCode);
                        newAlarm.setAlarmSettings(true);
                        alarmRepository.save(newAlarm);
                    }
                }
        );
    }
}
