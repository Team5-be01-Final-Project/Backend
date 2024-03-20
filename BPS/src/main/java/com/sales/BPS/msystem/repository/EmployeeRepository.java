package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entitiy.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
