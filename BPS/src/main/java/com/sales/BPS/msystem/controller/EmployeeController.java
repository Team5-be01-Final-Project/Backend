package com.sales.BPS.msystem.controller;

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.dto.EmployeesSpecDTO;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/employees")
@Tag(name = "System API", description = "시스템관리 API입니다.")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    @Tag(name = "System API")
    @Operation(summary = "사원정보 조회",description = "사원정보(부서이름,이름, 전화번호 조회")
    public List<EmployeeInfoDTO> searchEmployees(@RequestParam(required = false) String deptName,
                                                 @RequestParam(required = false) String empName,
                                                 @RequestParam(required = false) String empEmail) {
        return employeeService.findByCriteria(deptName, empName, empEmail);
    }

    @GetMapping("/{empCode}")
    @Tag(name = "System API")
    @Operation(summary = "사원 정보 조회", description = "로그인한 사원의 정보를 조회합니다.")
    public ResponseEntity<Employee> getEmployeeByCode(@PathVariable Integer empCode) {
        Employee employee = employeeService.findByEmpCode(empCode);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{empCode}/clients") //담당자의 담당 거래처 찾기
    public ResponseEntity<List<Client>> getClientsByEmployee(@PathVariable Integer empCode) {
        Employee employee = employeeService.findByEmpCode(empCode);
        return ResponseEntity.ok(employee.getClients());
    }

    @GetMapping("/{empCode}/approver") //담당자의 담당 거래처 찾기
    public ResponseEntity<?> getByDeptCodeAndPositionCode(@PathVariable Integer empCode) {
        String deptCode = employeeService.findByEmpCode(empCode).getDepartment().getDeptCode();
        Employee employee = employeeService.findByDeptCodeAndPositionCode(deptCode);
        if (employee != null) {
            Map<String, Object> approverInfo = new HashMap<>();
            approverInfo.put("signerName", employee.getEmpName());
            approverInfo.put("signerCode", employee.getEmpCode());
            return ResponseEntity.ok(approverInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 모든 직원의 사양을 조회하는 GET 요청 핸들러
    @GetMapping("/specs")
    public ResponseEntity<List<EmployeesSpecDTO>> getAllEmployeeSpecs() {
        List<EmployeesSpecDTO> employeesSpecs = employeeService.getAllEmployeesSpec();
        return ResponseEntity.ok(employeesSpecs); // HTTP 200 OK 상태 코드와 함께 결과 반환
    }
}
