package com.sales.BPS.service;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import com.sales.BPS.msystem.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void loginEmployee_WithValidCredentials_ShouldReturnTrue() {
        Integer empCode = 1;
        String empPw = "validPassword";

        // Mockito를 사용하여 Employee 객체 목업 생성
        Employee mockEmployee = mock(Employee.class);
        when(mockEmployee.getEmpPw()).thenReturn(empPw); // 비밀번호를 반환하도록 설정
        when(employeeRepository.findById(empCode)).thenReturn(Optional.of(mockEmployee));

        boolean isLoggedIn = employeeService.loginEmployee(empCode, empPw);

        assertTrue(isLoggedIn);
    }

    @Test
    void loginEmployee_WithInvalidCredentials_ShouldReturnFalse() {
        Integer empCode = 1;
        String empPw = "invalidPassword";

        // Mockito를 사용하여 Employee 객체 목업 생성
        Employee mockEmployee = mock(Employee.class);
        when(employeeRepository.findById(empCode)).thenReturn(Optional.of(mockEmployee));

        boolean isLoggedIn = employeeService.loginEmployee(empCode, empPw);

        assertFalse(isLoggedIn);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        // Mockito를 사용하여 Employee 객체 목업 생성
        Employee mockEmployee1 = mock(Employee.class);
        Employee mockEmployee2 = mock(Employee.class);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(mockEmployee1, mockEmployee2));

        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(2, employees.size());
    }

    @Test
    void findByCriteria_ShouldReturnFilteredEmployees() {
        String deptName = "HR";
        String empName = "John Doe";
        String empTel = "1234567890";

        // Mockito를 사용하여 EmployeeInfoDTO 객체 목업 생성
        EmployeeInfoDTO mockEmployeeInfoDTO = mock(EmployeeInfoDTO.class);
        when(mockEmployeeInfoDTO.getEmpName()).thenReturn(empName); // empName을 반환하도록 설정

        when(employeeRepository.findByCriteria(deptName, empName, empTel))
                .thenReturn(Arrays.asList(mockEmployeeInfoDTO));

        List<EmployeeInfoDTO> filteredEmployees = employeeService.findByCriteria(deptName, empName, empTel);

        assertNotNull(filteredEmployees);
        assertEquals(1, filteredEmployees.size());
        assertEquals(empName, filteredEmployees.get(0).getEmpName());
    }
}
