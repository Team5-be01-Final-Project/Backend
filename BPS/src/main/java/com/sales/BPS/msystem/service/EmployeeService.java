package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.dto.EmployeesSpecDTO;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private static final String SECRET_KEY = "new_secret_key_here";  // 적절한 키 값을 설정하세요.

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }



//    @PostConstruct
//    public void initialize() {
//        updateExistingPasswords();  // 애플리케이션이 시작할 때 비밀번호 업데이트 실행
//    }
//
//    public void updateExistingPasswords() {
//        List<Employee> employees = employeeRepository.findAll();
//        for (Employee employee : employees) {
//            try {
//                String currentPassword = employee.getEmpPw();
//                String newPassword = EncryptionService.encrypt(currentPassword);
//                employee.setEmpPw(newPassword);
//                employeeRepository.save(employee);
//            } catch (Exception e) {
//                // 예외 처리
//                System.err.println("Failed to update password for employee ID " + employee.getEmpCode() + ": " + e.getMessage());
//            }
//        }
//    }

    public boolean loginEmployee(Integer empCode, String empPw) {
        logger.info("Attempting to log in user with empCode: {}", empCode);
        Optional<Employee> employeeOptional = employeeRepository.findById(empCode);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if(employee.getAuthority().getAuthCode().equals("AUTH000")){
                logger.warn("A retired employee with empCode: {}", empCode);
                return false;
            }
            try {
                String encryptedInputPw = EncryptionService.encryptWithNewKey(empPw, SECRET_KEY);
                logger.debug("Encrypted input password: {}", encryptedInputPw); // 암호화된 입력 비밀번호 로깅
                logger.debug("Stored encrypted password: {}", employee.getEmpPw()); // 저장된 암호화된 비밀번호 로깅
                logger.info("Attempting to log in user with empCode: {}, Encrypted PW from input: {}, Stored PW: {}", empCode, encryptedInputPw, employee.getEmpPw());
                boolean loginSuccess = empPw.equals(employee.getEmpPw());
                logger.info("Login attempt for empCode {}: {}", empCode, loginSuccess ? "SUCCESS" : "FAILURE");
                logger.info("Attempting to log in user with empCode: {}, Encrypted PW from input: {}, Stored PW: {}", empCode, encryptedInputPw, employee.getEmpPw());
                return loginSuccess;
            } catch (Exception e) {
                logger.error("Error during login for empCode {}: {}", empCode, e.getMessage());
                return false;
            }
        } else {
            logger.warn("No employee found with empCode: {}", empCode);
            return false;
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

    // 사원 정보 조회 메서드
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
                employee.getEmpEmail(),
                employee.getEmpTel(),
                employee.getEmpStartDate(),
                employee.getEmpEndDate()
        )).collect(Collectors.toList());
    }

    // 로그인한 사원의 정보를 specDTO에서 조회
    public EmployeesSpecDTO findByEmpCodeWithSpec(Integer empCode) {
        return employeeRepository.findByEmpCodeWithSpec(empCode)
                .orElseThrow(() -> new NoSuchElementException("해당하는 직원을 찾을 수 없습니다. EmpCode: " + empCode));
    }

}
