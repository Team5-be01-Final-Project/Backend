package com.sales.BPS.msystem.controller;

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.service.ClientService;
import com.sales.BPS.msystem.dto.EmployeesSpecDTO;
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
        // empCode에 해당하는 사원 정보를 EmployeesSpecDTO 형태로 조회
        EmployeesSpecDTO employeeSpec = employeeService.findByEmpCodeWithSpec(empCode);

        // empCode에 해당하는 사원의 담당 거래처 목록을 조회
        List<Client> clients = clientService.findByEmployeeEmpCode(empCode);

        // 응답 본문으로 반환할 Map 객체 생성
        Map<String, Object> response = new HashMap<>();

        // 사원 정보를 "employee" 키에 저장
        response.put("employee", employeeSpec);

        // 거래처 목록을 "clients" 키에 저장
        response.put("clients", clients);

        // HTTP 200 OK 상태 코드와 함께 응답 본문 반환
        return ResponseEntity.ok(response);
    }
}
