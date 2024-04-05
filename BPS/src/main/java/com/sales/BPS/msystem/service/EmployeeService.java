package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.dto.EmployeesSpecDTO;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    // 생성자 주입 방식을 사용합니다.
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    // 직원 로그인 검증 메서드
    public boolean loginEmployee(Integer empCode, String empPw) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empCode);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            String storedEmpPw = employee.getEmpPw();
            return empPw.equals(storedEmpPw); // 비밀번호 일치 여부 반환
        } else {
            return false; // 해당 직원 코드로 검색된 직원이 없는 경우
        }
    }
    // 기준에 따른 직원 검색
    public List<EmployeeInfoDTO> findByCriteria(String deptName, String empName, String empEmail) {
        return employeeRepository.findByCriteria(deptName, empName, empEmail);
    }

    // 모든 직원 목록을 반환하는 메서드
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findByEmpCode(Integer empCode){
        return employeeRepository.findById(empCode)
                .orElseThrow(() -> new NoSuchElementException("해당하는 직원을 찾을 수 없습니다. EmpCode: " + empCode));
    }

    public Employee findByDeptCodeAndPositionCode(String deptCode){
        Employee manager = employeeRepository.findByDeptCodeAndPositionCode2(deptCode, "P02");
        return manager;
    }


    public List<EmployeesSpecDTO> getAllEmployeesSpec() {
        List<Employee> employees = employeeRepository.findAll();
        // Employee 엔터티 리스트를 EmployeesSpecDTO 리스트로 변환
        return employees.stream().map(employee -> new EmployeesSpecDTO(
                employee.getEmpImg(),
                employee.getEmpCode(),
                employee.getEmpName(),
                employee.getPositions().getPosName(),
                employee.getDepartment().getDeptName(),
                employee.getEmpTel(),
                employee.getEmpEmail(),
                employee.getEmpStartDate(),
                employee.getEmpEndDate()
        )).collect(Collectors.toList());
    }

}
