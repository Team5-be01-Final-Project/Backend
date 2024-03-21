package com.sales.BPS.msystem.service;

import java.util.List;

import com.sales.BPS.msystem.entity.Employee;

public interface EmployeeService {

    List<Employee> (String bId);
    Employee getEmployeeByCode(String employeeId);
    Employee updateEmployee(String employeeId, Employee updatedEmployee);
    void deleteEmployee(String employeeId);
    boolean loginEmployee(String employeeId, String bpwd);

    // 포스트맨
    Business saveB(Business business);					// 사업자 추가
    Business getBByCode(String bcode); 					// 사업자 정보 보기
    List<Business> getAllB();							// 전체 사업자 보기
    Business updateB(String bcode, Business business);	// 사업자 정보 업데이트
    void deleteBByCode(String bcode); 					// 사업자 삭제

}



