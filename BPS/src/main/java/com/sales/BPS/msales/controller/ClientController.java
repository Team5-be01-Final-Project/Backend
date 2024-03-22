package com.sales.BPS.msales.controller;


import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msales.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        Client savedClient = clientService.saveClient(client);
        return ResponseEntity.ok(savedClient);
    }

    @GetMapping("/list")
    public List<ClientRepository.ClientProjection> getClients() {
        return clientService.getClientsWithSpecificFields();
    }


    @PutMapping("/{clientCode}")
    public ResponseEntity<Client> updateClient(@PathVariable String clientCode, @RequestBody Client client){
        Client updatedClient = clientService.updateClient(clientCode,client);
        return ResponseEntity.ok(updatedClient);
    }

}

