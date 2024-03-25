package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


//    List<Employee> findAll();

//    @Query("SELECT new com.sales.BPS.msystem.dto.EmployeeInfoDTO" +
//            "(e.empCode, e.empName, p.posName, d.deptName, e.empTel, ac.authName) " +
//            "FROM Employee e " +
//            "JOIN e.department d " +
//            "JOIN e.positions p " +
//            "JOIN e.authority a " +
//            "JOIN a.authorityCode ac")
//    List<EmployeeInfoDTO> findAllEmployees();


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
}
