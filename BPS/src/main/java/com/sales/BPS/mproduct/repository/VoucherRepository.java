package com.sales.BPS.mproduct.repository;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.entity.VoucherPK;
import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, VoucherPK> {
    @Query("SELECT new com.sales.BPS.msystem.dto.EmployeeInfoDTO(e.empCode, e.empName, p.posName, d.deptName, e.empTel,ac.authCode, ac.authName) " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "JOIN e.positions p " +
            "JOIN e.authority a " +
            "JOIN a.authorityCode ac " +
            "WHERE (:deptName IS NULL OR d.deptName LIKE %:deptName%) " +
            "AND (:empTel IS NULL OR e.empTel LIKE %:empTel%)")
    List<EmployeeInfoDTO> findByCriteria(@Param("deptName") String deptName,
                                         @Param("empName") String empName,
                                         @Param("empTel") String empTel);
}