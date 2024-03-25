package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<EmployeeInfoDTO> findByCriteria(String deptName, String empName, String empTel) {
        return employeeRepository.findByCriteria(deptName, empName, empTel);
    }

    // 모든 직원 목록을 반환하는 메서드
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
