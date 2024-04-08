package com.sales.BPS.mproduct.repository;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.entity.VoucherPK;
import com.sales.BPS.msales.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, VoucherPK> {

    // 직원별, 월별, 매출 데이터를 매출 금액이 큰 순으로 가져옴
    @Query("SELECT v.employee.empCode, SUM(v.voucSales) FROM Voucher v WHERE MONTH(v.voucDate) = :month GROUP BY v.employee.empCode ORDER BY SUM(v.voucSales) DESC")
    List<Object[]> findSalesByEmployeeAndMonth(int month);


    // 직원별, 연도별, 매출 데이터를 매출 금액이 큰 순으로 가져옴

    @Query("SELECT v.employee.empCode, SUM(v.voucSales) FROM Voucher v WHERE YEAR(v.voucDate) = :year AND MONTH(v.voucDate) = :month GROUP BY v.employee.empCode ORDER BY SUM(v.voucSales) DESC")
    List<Object[]> findSalesByEmployeeAndYearAndMonth(@Param("year") int year, @Param("month") int month);


    List<Voucher> findByVoucId(Long voucId);

    List<Voucher> findByClient(Client client);


    @Query("SELECT v FROM Voucher v WHERE FUNCTION('YEAR', v.voucApproval) = :year AND FUNCTION('MONTH', v.voucApproval) = :month")
    List<Voucher> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT v FROM Voucher v WHERE v.client.clientCode = :clientCode AND FUNCTION('YEAR', v.voucApproval) = :year AND FUNCTION('MONTH', v.voucApproval) = :month")
    List<Voucher> findByClientClientCodeAndYearAndMonth(@Param("clientCode") String clientCode, @Param("year") int year, @Param("month") int month);

    @Query("SELECT v FROM Voucher v WHERE YEAR(v.voucApproval) = :year")
    List<Voucher> findAllByYear(@Param("year") int year);

    // 사원의 매출 데이터를 연도와 월 별로 조회하는 JPA 쿼리
    @Query("SELECT v.employee.empCode, SUM(v.voucSales) " +
            "FROM Voucher v " +
            "WHERE v.employee.empCode = :empCode AND YEAR(v.voucApproval) = :year AND MONTH(v.voucApproval) = :month " +
            "GROUP BY v.employee.empCode")
    Object[] findSalesByEmployeeCodeAndYearAndMonth(@Param("empCode") int empCode, @Param("year") int year, @Param("month") int month);
}
