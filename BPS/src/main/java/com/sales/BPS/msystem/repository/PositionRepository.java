package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Positions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Positions, String> {
}
