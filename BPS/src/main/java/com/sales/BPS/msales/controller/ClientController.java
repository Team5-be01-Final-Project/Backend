package com.sales.BPS.msales.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.BPS.msales.dto.ClientDTO;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msales.service.ClientService;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientController(ClientService clientService, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
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
    public List<ClientRepository.ClientProjection> getClients() {
        return clientService.getClientsWithSpecificFields();
    }



    @PutMapping("/{clientCode}")
    public ResponseEntity<Client> updateClient(@PathVariable String clientCode, @RequestBody ClientDTO clientDTO){
        clientDTO.setClientCode(clientCode);
        Client updatedClient = clientService.saveOrUpdateClient(clientDTO);
        return ResponseEntity.ok(updatedClient);
    }


    @DeleteMapping("/{clientCode}")
    public ResponseEntity<?> deleteClient(@PathVariable String clientCode) {
        try {
            clientService.deleteClientByClientCode(clientCode);
            return ResponseEntity.ok().body("Client with code " + clientCode + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{clientCode}")
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
}

