package com.sales.BPS.msystem.controller;

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.service.ClientService;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mysales")
@Tag(name = "MySales API", description = "My 영업 API입니다.")
public class MySalesController {

    private final EmployeeService employeeService;
    private final ClientService clientService;

    @Autowired
    public MySalesController(EmployeeService employeeService, ClientService clientService) {
        this.employeeService = employeeService;
        this.clientService = clientService;
    }

    @GetMapping("/{empCode}")
    @Tag(name = "MySales API")
    @Operation(summary = "My 영업 정보 조회", description = "로그인한 사원의 정보와 담당 거래처 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getMySalesInfo(@PathVariable Integer empCode) {
        Employee employee = employeeService.findByEmpCode(empCode);
        List<Client> clients = clientService.findByEmployeeEmpCode(empCode);

        Map<String, Object> response = new HashMap<>();
        response.put("employee", employee);
        response.put("clients", clients);

        return ResponseEntity.ok(response);
    }
}
