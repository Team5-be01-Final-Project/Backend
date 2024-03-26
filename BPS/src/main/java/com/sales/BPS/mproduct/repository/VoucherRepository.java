package com.sales.BPS.mproduct.repository;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.entity.VoucherPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, VoucherPK> {

    // 직원별, 월별, 매출 데이터를 매출 금액이 큰 순으로 가져옴
    @Query("SELECT v.employee.empCode, SUM(v.voucSales) FROM Voucher v WHERE MONTH(v.voucDate) = :month GROUP BY v.employee.empCode ORDER BY SUM(v.voucSales) DESC")
    List<Object[]> findSalesByEmployeeAndMonth(int month);
}