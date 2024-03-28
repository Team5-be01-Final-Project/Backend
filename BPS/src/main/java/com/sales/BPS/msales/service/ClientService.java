package com.sales.BPS.msales.service;

import com.sales.BPS.msales.dto.ClientDTO;
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
        }else {
            client.setEmployee(null);
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

    public Client saveOrUpdateClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setClientCode(clientDTO.getClientCode());
        client.setClientName(clientDTO.getClientName());
        client.setClientClass(clientDTO.getClientClass());
        client.setClientBoss(clientDTO.getClientBoss());
        client.setClientWhere(clientDTO.getClientWhere());
        client.setClientPost(clientDTO.getClientPost());
        client.setClientEmp(clientDTO.getClientEmp());
        client.setClientEmpTel(clientDTO.getClientEmpTel());
        client.setClientStart(clientDTO.getClientStart());
        client.setClientEnd(clientDTO.getClientEnd());
        client.setClientNote(clientDTO.getClientNote());
        if (clientDTO.getEmpCode() != null) {
            Employee employee = employeeRepository.findById(clientDTO.getEmpCode())
                    .orElseThrow(() -> new RuntimeException("Employee with empCode " + clientDTO.getEmpCode() + " not found"));
            client.setEmployee(employee);
        }

        return clientRepository.save(client);
    }

    public ClientDTO convertToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setClientCode(client.getClientCode());
        dto.setClientName(client.getClientName());
        dto.setClientClass(client.getClientClass());
        dto.setClientBoss(client.getClientBoss());
        dto.setClientWhere(client.getClientWhere());
        dto.setClientPost(client.getClientPost());
        dto.setClientEmp(client.getClientEmp());
        dto.setClientEmpTel(client.getClientEmpTel());
        dto.setClientStart(client.getClientStart());
        dto.setClientEnd(client.getClientEnd());
        dto.setClientNote(client.getClientNote());
        if (client.getEmployee() != null) {
            dto.setEmpCode(client.getEmployee().getEmpCode());
        }
        return dto;
    }

    public ClientDTO findByClientCode(String clientCode) {
        Optional<Client> client = clientRepository.findById(clientCode);
        if (client.isPresent()) {
            return convertToDTO(client.get());
        } else {
            return null;
        }
    }
}
