package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
