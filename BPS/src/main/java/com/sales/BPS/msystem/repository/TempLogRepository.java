package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.TempLog;
import com.sales.BPS.msystem.entity.TempLogPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempLogRepository extends JpaRepository<TempLog, TempLogPK> {
    List<TempLog> findByTempTempLessThanOrTempTempGreaterThan(int min, int max);
}