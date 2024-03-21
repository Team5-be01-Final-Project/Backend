package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    List<Employee> findAll();


}
