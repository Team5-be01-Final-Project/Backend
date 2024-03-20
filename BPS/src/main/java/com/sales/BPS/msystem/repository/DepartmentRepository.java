package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
