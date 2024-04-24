package com.sales.BPS.service;

import com.sales.BPS.msystem.dto.EmployeeInfoDTO;
import com.sales.BPS.msystem.dto.EmployeesSpecDTO;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import com.sales.BPS.msystem.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("로그인 유효한 자격 증명")
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
    @DisplayName("로그인 유효하지 않은 자격 증명")
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
    @DisplayName("모든 직원을 가져오는 메소드가 올바른 리스트를 반환하는지")
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
    @DisplayName("기준에 따라 직원을 필터링하는 메소드가 올바른 결과를 반환하는지")
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

    @Test
    @DisplayName("올바른 empCode로 직원 조회 시 직원 정보 반환")
    void findByEmpCode_WithValidCode_ShouldReturnEmployee() {
        Integer empCode = 1;
        Employee mockEmployee = new Employee();
        mockEmployee.setEmpCode(empCode);
        mockEmployee.setEmpName("John Doe");

        when(employeeRepository.findById(empCode)).thenReturn(Optional.of(mockEmployee));

        Employee foundEmployee = employeeService.findByEmpCode(empCode);

        assertNotNull(foundEmployee, "직원 객체는 null이 아니어야 합니다.");
        assertEquals(empCode, foundEmployee.getEmpCode(), "조회된 직원 코드가 요청한 empCode와 일치해야 합니다.");
        assertEquals("John Doe", foundEmployee.getEmpName(), "조회된 직원 이름이 기대한 값과 일치해야 합니다.");
    }

    @Test
    @DisplayName("잘못된 empCode로 직원 조회 시 예외 발생")
    void findByEmpCode_WithInvalidCode_ShouldThrowException() {
        Integer empCode = 999;  // 존재하지 않는 empCode

        when(employeeRepository.findById(empCode)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            employeeService.findByEmpCode(empCode);
        }, "존재하지 않는 empCode로 조회 시 NoSuchElementException이 발생해야 합니다.");
    }

    @Test
    @DisplayName("특정 부서 코드와 직급 코드로 직원 조회")
    void findByDeptCodeAndPositionCode_ShouldReturnEmployee() {
        String deptCode = "HR";
        String positionCode = "P02";  // 매니저 포지션
        Employee mockManager = new Employee();
        mockManager.setEmpCode(1);
        mockManager.setEmpName("John Manager");

        when(employeeRepository.findByDeptCodeAndPositionCode2(deptCode, positionCode)).thenReturn(mockManager);

        Employee foundManager = employeeService.findByDeptCodeAndPositionCode(deptCode);

        assertNotNull(foundManager, "반환된 직원 객체는 null이 아니어야 합니다.");
        assertEquals("John Manager", foundManager.getEmpName(), "반환된 매니저 이름이 기대한 값과 일치해야 합니다.");
    }

    @Test
    @DisplayName("모든 직원의 사양을 DTO로 반환")
    void getAllEmployeesSpec_ShouldReturnListOfEmployeeSpecDTOs() {
        // 목업 데이터 생성
        Employee emp1 = new Employee();
        emp1.setEmpCode(1);
        emp1.setEmpName("John Doe");
        emp1.setEmpEmail("johndoe@example.com");
        emp1.setEmpTel("1234567890");
        emp1.setEmpStartDate(LocalDate.parse("2021-01-01"));
        emp1.setEmpEndDate(LocalDate.parse("2022-01-01"));

        Employee emp2 = new Employee();
        emp2.setEmpCode(2);
        emp2.setEmpName("Jane Doe");
        emp2.setEmpEmail("janedoe@example.com");
        emp2.setEmpTel("0987654321");
        emp2.setEmpStartDate(LocalDate.parse("2021-02-01"));
        emp2.setEmpEndDate(LocalDate.parse("2022-02-01"));

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        // 테스트 실행
        List<EmployeesSpecDTO> specs = employeeService.getAllEmployeesSpec();

        // 검증
        assertNotNull(specs, "DTO 리스트는 null이 아니어야 합니다.");
        assertEquals(2, specs.size(), "DTO 리스트의 크기는 2여야 합니다.");
        assertEquals("John Doe", specs.get(0).getEmpName(), "첫 번째 직원의 이름이 일치해야 합니다.");
        assertEquals("Jane Doe", specs.get(1).getEmpName(), "두 번째 직원의 이름이 일치해야 합니다.");
    }

    @Test
    @DisplayName("유효한 직원 코드로 EmployeesSpecDTO 조회")
    void findByEmpCodeWithSpec_WithValidEmpCode_ShouldReturnEmployeeSpecDTO() {
        Integer empCode = 1;
        EmployeesSpecDTO mockSpecDTO = new EmployeesSpecDTO(
                "image.png", empCode, "John Doe", "Manager", "HR", "john.doe@example.com",
                "1234567890", LocalDate.parse("2021-01-01"), LocalDate.parse("2022-01-01")
        );

        when(employeeRepository.findByEmpCodeWithSpec(empCode)).thenReturn(Optional.of(mockSpecDTO));

        EmployeesSpecDTO foundSpec = employeeService.findByEmpCodeWithSpec(empCode);

        assertNotNull(foundSpec, "EmployeesSpecDTO 객체는 null이 아니어야 합니다.");
        assertEquals("John Doe", foundSpec.getEmpName(), "조회된 직원 이름이 기대한 값과 일치해야 합니다.");
        assertEquals("HR", foundSpec.getDeptName(), "조회된 직원의 부서 이름이 기대한 값과 일치해야 합니다.");
    }

    @Test
    @DisplayName("존재하지 않는 직원 코드로 EmployeesSpecDTO 조회 시 예외 발생")
    void findByEmpCodeWithSpec_WithInvalidEmpCode_ShouldThrowException() {
        Integer empCode = 999;  // 존재하지 않는 직원 코드

        when(employeeRepository.findByEmpCodeWithSpec(empCode)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            employeeService.findByEmpCodeWithSpec(empCode);
        }, "존재하지 않는 직원 코드로 조회 시 NoSuchElementException이 발생해야 합니다.");
    }

}
