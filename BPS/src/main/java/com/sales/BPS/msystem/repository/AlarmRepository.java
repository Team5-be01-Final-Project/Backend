package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Alarm;
import com.sales.BPS.msystem.entity.AlarmPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, AlarmPK> {
    Optional<Alarm> findByEmpCodeAndAlarmCode(Integer empCode, String alarmCode);

    List<Alarm> findByEmpCode(Integer empCode);
}
