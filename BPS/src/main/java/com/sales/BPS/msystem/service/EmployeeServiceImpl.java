package com.sales.BPS.msystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public boolean loginEmployee(Integer empCode, String empPw) {
        return employeeRepository.findById(empCode)
                .map(employee -> employee.getEmpPw().equals(empPw))
                .orElse(false);
    }

    @Override
    public List<Employee> listAllEmployee() {
        return employeeRepository.findAll();
    }

}