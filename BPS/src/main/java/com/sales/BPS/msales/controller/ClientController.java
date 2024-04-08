package com.sales.BPS.msales.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.BPS.config.CookieUtil;
import com.sales.BPS.msales.dto.ClientDTO;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msales.service.ClientService;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    @Operation(summary = "거래처 삭제", description = "거래처를 삭제합니다.")
    public ResponseEntity<?> deleteClient(@PathVariable String clientCode, HttpServletRequest request) {
        try {
            // 쿠키에서 권한 코드 가져오기
            String authCode = CookieUtil.getCookieValue(request, "empAuthCode");

            // 권한 검사
            if (!"AUTH003".equals(authCode)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
            }

            clientService.deleteClientByClientCode(clientCode);
            return ResponseEntity.ok().body("Client with code " + clientCode + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{clientCode}")
    @Tag(name = "Client API")
    @Operation(summary = "수정 전 조회", description = "거래처 수정 전 거래처를 조회합니다.")
    public ResponseEntity<ClientDTO> getClientByCode(@PathVariable String clientCode) {
        try {
            // clientService의 findByClientCode 메서드를 호출하여 clientCode에 해당하는 ClientDTO를 가져옵니다.
            ClientDTO clientDTO = clientService.findByClientCode(clientCode);

            if (clientDTO != null) {
                // clientDTO가 null이 아니면 ResponseEntity의 ok 메서드를 사용하여 200 OK 상태코드와 함께 clientDTO를 반환합니다.
                return ResponseEntity.ok(clientDTO);
            } else {
                // clientDTO가 null이면 ResponseEntity의 notFound 메서드를 사용하여 404 Not Found 상태코드를 반환합니다.
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 예외가 발생한 경우 ResponseEntity의 status 메서드를 사용하여 500 Internal Server Error 상태코드와 함께 null을 반환합니다.
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

