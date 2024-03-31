package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT new com.sales.BPS.msystem.dto.EmployeeInfoDTO(e.empCode, e.empName, p.posName, d.deptName, e.empTel,ac.authCode, ac.authName) " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "JOIN e.positions p " +
            "JOIN e.authority a " +
            "JOIN a.authorityCode ac " +
            "WHERE (:deptName IS NULL OR d.deptName LIKE %:deptName%) " +
            "AND (:empName IS NULL OR e.empName LIKE %:empName%) " +
            "AND (:empTel IS NULL OR e.empTel LIKE %:empTel%)")
    List<EmployeeInfoDTO> findByCriteria(@Param("deptName") String deptName,
                                         @Param("empName") String empName,
                                         @Param("empTel") String empTel);



    // 부서코드와, 직급코드로 해당하는 사람 이름 찾기
    @Query("SELECT e FROM Employee e JOIN e.department d JOIN e.positions p WHERE d.deptCode = :deptCode AND p.posCode = :posCode")
    Employee findByDeptCodeAndPositionCode(@Param("deptCode") String deptCode, @Param("posCode") String posCode);


}
