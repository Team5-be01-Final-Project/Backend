package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }


//    public List<Employee> findAll(){
//        return employeeRepository.findAll();
//    }

//    public List<EmployeeInfoDTO> findAllEmployees(){
//        return employeeRepository.findAllEmployees();
//    }

    public List<EmployeeInfoDTO> findByCriteria(String deptName, String empName, String empTel) {
        return employeeRepository.findByCriteria(deptName, empName, empTel);
    }
}
