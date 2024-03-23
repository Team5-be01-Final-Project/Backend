package com.sales.BPS.msales.service;

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, EmployeeRepository employeeRepository) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client saveClient(Client client, Integer empCode) throws Exception {
        if (clientRepository.existsByClientCode(client.getClientCode())) {
            throw new Exception("해당 클라이언트 코드는 이미 등록되었습니다.");
        }

        // empCode를 사용하여 Employee 엔티티 연결
        if (empCode != null) {
            Employee employee = employeeRepository.findById(empCode)
                    .orElseThrow(() -> new RuntimeException("해당 empCode를 가진 직원을 찾을 수 없습니다: " + empCode));
            client.setEmployee(employee);
        }

        return clientRepository.save(client);
    }

    public List<ClientRepository.ClientProjection> getClientsWithSpecificFields() {
        return clientRepository.findClientsWithSpecificFields();
    }

    public Client findClientByClientCode(String clientCode) {
        Optional<Client> client = clientRepository.findById(clientCode);
        return client.orElse(null); // 클라이언트 객체가 존재하면 반환하고, 그렇지 않으면 null을 반환
    }

    public void deleteClientByClientCode(String clientCode) throws Exception {
        if (clientRepository.existsById(clientCode)) {
            clientRepository.deleteById(clientCode);
        } else {
            throw new Exception("Client with code " + clientCode + " does not exist.");
        }
    }

    public Client updateClient(String clientCode, Client updatedClient) {
        Optional<Client> optionalClient = clientRepository.findById(clientCode);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setClientName(updatedClient.getClientName());
            client.setClientClass(updatedClient.getClientClass());
            client.setClientBoss(updatedClient.getClientBoss());
            client.setClientWhere(updatedClient.getClientWhere());
            client.setClientPost(updatedClient.getClientPost());
            client.setClientEmp(updatedClient.getClientEmp());
            client.setClientEmpTel(updatedClient.getClientEmpTel());
            return clientRepository.save(client);
        } else {
            return null;
        }
    }
}
