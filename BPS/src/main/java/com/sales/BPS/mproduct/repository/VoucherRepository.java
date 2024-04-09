package com.sales.BPS.mproduct.repository;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.entity.VoucherPK;
import com.sales.BPS.msales.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    // 검색 조건에 맞는 출고전표를 조회
    @Query("SELECT v FROM Voucher v WHERE (:voucId IS NULL OR CAST(v.voucId AS string) LIKE %:voucId%) " +
            "AND (:empName IS NULL OR v.employee.empName LIKE %:empName%) " +
            "AND (:clientName IS NULL OR v.client.clientName LIKE %:clientName%) " +
            "AND (:startDate IS NULL OR v.voucDate >= :startDate) " +
            "AND (:endDate IS NULL OR v.voucDate <= :endDate) " +
            "AND (:signerName IS NULL OR v.employeeSign.empName LIKE %:signerName%) " +
            "AND (:approvalStatus IS NULL OR v.approvalCode.appName LIKE %:approvalStatus%) " +
            "AND (:approvalStartDate IS NULL OR v.voucApproval >= :approvalStartDate) " +
            "AND (:approvalEndDate IS NULL OR v.voucApproval <= :approvalEndDate)")
    List<Voucher> searchVouchers(
            @Param("voucId") String voucId, // 전표번호로 검색 (선택)
            @Param("empName") String empName, // 담당자 이름으로 검색 (선택)
            @Param("clientName") String clientName, // 거래처명으로 검색 (선택)
            @Param("startDate") LocalDate startDate, // 등록일 시작일로 검색 (선택)
            @Param("endDate") LocalDate endDate, // 등록일 종료일로 검색 (선택)
            @Param("signerName") String signerName, // 결재자 이름으로 검색 (선택)
            @Param("approvalStatus") String approvalStatus, // 결재상태로 검색 (선택)
            @Param("approvalStartDate") LocalDate approvalStartDate, // 결재일 시작일로 검색 (선택)
            @Param("approvalEndDate") LocalDate approvalEndDate); // 결재일 종료일로 검색 (선택)

}
