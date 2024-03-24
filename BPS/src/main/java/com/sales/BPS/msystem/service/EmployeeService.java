package com.sales.BPS.msystem.service;

import java.util.List;

import com.sales.BPS.msystem.entity.Employee;

public interface EmployeeService {

    boolean loginEmployee(Integer empCode, String empPw);            // login

}