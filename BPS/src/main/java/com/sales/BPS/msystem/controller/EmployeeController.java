package com.sales.BPS.msystem.controller;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

//    @GetMapping("/list")
//    public List<EmployeeInfoDTO> getAllEmployees(){
//        return employeeService.findAllEmployees();
//    }

    @GetMapping("/list")
    public List<EmployeeInfoDTO> searchEmployees(@RequestParam(required = false) String deptName,
                                                 @RequestParam(required = false) String empName,
                                                 @RequestParam(required = false) String empTel) {
        return employeeService.findByCriteria(deptName, empName, empTel);
    }

}
