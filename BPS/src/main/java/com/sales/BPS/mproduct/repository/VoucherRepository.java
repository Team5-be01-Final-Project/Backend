package com.sales.BPS.mproduct.repository;

// VoucherRepository.java


import com.sales.BPS.mproduct.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

}
