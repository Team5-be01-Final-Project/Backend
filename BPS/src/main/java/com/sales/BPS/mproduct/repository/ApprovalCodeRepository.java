package com.sales.BPS.mproduct.repository;

import com.sales.BPS.mproduct.entity.ApprovalCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalCodeRepository extends JpaRepository<ApprovalCode, String> {
}