package com.sales.BPS.msales.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.BPS.msales.dto.ClientDTO;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msales.service.ClientService;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client API", description = "거래처관리 API입니다.")
public class ClientController {

    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientController(ClientService clientService, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    @Tag(name = "Client API")
    @Operation(summary = "거래처 등록",description = "거래처를 등록합니다.")
    public ResponseEntity<?> addClient(@RequestBody Map<String, Object> payload) {
        try {
            // empCode를 제외한 Client 데이터 역직렬화
            Integer empCode = (Integer) payload.get("empCode");
            payload.remove("empCode"); // payload에서 empCode 제거
            Client client = objectMapper.convertValue(payload, Client.class);

            Client savedClient = clientService.saveClient(client, empCode);
            return ResponseEntity.ok(savedClient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    @Tag(name = "Client API")
    @Operation(summary = "거래처 조회",description = "거래처를 조회합니다.")
    public List<ClientRepository.ClientProjection> getClients() {
        return clientService.getClientsWithSpecificFields();
    }



    @PutMapping("/{clientCode}")
    @Tag(name = "Client API")
    @Operation(summary = "ClientDTO update",description = "ClientDTO에 정보를 업데이트 합니다.")
    public ResponseEntity<Client> updateClient(@PathVariable String clientCode, @RequestBody ClientDTO clientDTO){
        clientDTO.setClientCode(clientCode);
        Client updatedClient = clientService.saveOrUpdateClient(clientDTO);
        return ResponseEntity.ok(updatedClient);
    }


    @DeleteMapping("/{clientCode}")
    @Tag(name = "Client API")
    @Operation(summary = "거래처 삭제",description = "거래처를 삭제합니다.")
    public ResponseEntity<?> deleteClient(@PathVariable String clientCode) {
        try {
            clientService.deleteClientByClientCode(clientCode);
            return ResponseEntity.ok().body("Client with code " + clientCode + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{clientCode}")
    @Tag(name = "Client API")
    @Operation(summary = "수정 전 조회",description = "거래처 수정 전 거래처를 조회합니다.")
    public ResponseEntity<ClientDTO> getClientByCode(@PathVariable String clientCode) {
        try {
            ClientDTO clientDTO = clientService.findByClientCode(clientCode);
            if (clientDTO != null) {
                return ResponseEntity.ok(clientDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 로그인한 사원의 담당 거래처 목록을 조회
    @GetMapping("/employee/{empCode}")
    @Tag(name = "Client API")
    @Operation(summary = "담당 거래처 조회", description = "로그인한 사원의 담당 거래처 목록을 조회합니다.")
    public ResponseEntity<List<Client>> getClientsByEmployeeCode(@PathVariable Integer empCode) {
        List<Client> clients = clientService.findByEmployeeEmpCode(empCode);
        return ResponseEntity.ok(clients);
    }
}

